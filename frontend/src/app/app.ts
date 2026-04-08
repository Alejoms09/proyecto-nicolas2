import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { finalize } from 'rxjs';
import { BookitApiService } from './bookit-api';
import { Appointment, AppointmentStatus, ServiceItem } from './bookit-models';

// Componente principal: formulario + listado de citas.
@Component({
  selector: 'app-root',
  imports: [FormsModule, NgFor, NgIf, NgClass],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  // Servicio HTTP para consumir backend.
  private readonly api = inject(BookitApiService);

  // Opciones de filtro visibles en la UI.
  protected readonly statuses: Array<AppointmentStatus | 'ALL'> = ['ALL', 'PENDING', 'DONE', 'CANCELLED'];

  // Catalogo de servicios.
  protected services: ServiceItem[] = [];
  // Citas actualmente visibles en pantalla.
  protected appointments: Appointment[] = [];
  // Citas completas sin filtro (cache local).
  protected allAppointments: Appointment[] = [];
  // Filtro actualmente activo.
  protected selectedStatus: AppointmentStatus | 'ALL' = 'ALL';

  // Estados de UI para loaders y bloqueos de boton.
  protected isLoadingServices = false;
  protected isLoadingAppointments = false;
  protected isSubmitting = false;

  // Mensajes informativos para usuario.
  protected errorMessage = '';
  protected successMessage = '';

  // Modelo del formulario de creacion.
  protected form = {
    clientName: '',
    clientEmail: '',
    date: '',
    time: '',
    serviceId: ''
  };

  ngOnInit(): void {
    // Carga inicial de catalogo y citas.
    this.loadServices();
    this.loadAppointments();
  }

  // Descarga catalogo de servicios para el select del formulario.
  protected loadServices(): void {
    this.errorMessage = '';
    this.isLoadingServices = true;
    this.api.getServices()
      .pipe(finalize(() => this.isLoadingServices = false))
      .subscribe({
        next: (services) => this.services = services,
        error: () => this.errorMessage = 'No fue posible cargar los servicios.'
      });
  }

  // Descarga todas las citas y aplica filtro local para una UI mas reactiva.
  protected loadAppointments(): void {
    this.errorMessage = '';
    this.isLoadingAppointments = true;
    this.api.getAppointments(null)
      .pipe(finalize(() => this.isLoadingAppointments = false))
      .subscribe({
        next: (appointments) => {
          this.allAppointments = appointments;
          this.applyFilter();
        },
        error: () => this.errorMessage = 'No fue posible cargar las citas.'
      });
  }

  // Cambia filtro sin nueva llamada de red.
  protected onFilterChange(status: AppointmentStatus | 'ALL'): void {
    this.selectedStatus = status;
    this.applyFilter();
  }

  // Valida formulario y crea una nueva cita.
  protected createAppointment(): void {
    this.errorMessage = '';
    this.successMessage = '';

    // Validacion simple de campos obligatorios en frontend.
    if (!this.form.clientName || !this.form.clientEmail || !this.form.date || !this.form.time || !this.form.serviceId) {
      this.errorMessage = 'Todos los campos son obligatorios.';
      return;
    }

    this.isSubmitting = true;
    this.api.createAppointment({
      clientName: this.form.clientName.trim(),
      clientEmail: this.form.clientEmail.trim(),
      date: this.form.date,
      time: this.form.time,
      serviceId: Number(this.form.serviceId)
    })
      .pipe(finalize(() => this.isSubmitting = false))
      .subscribe({
        next: (appointment) => {
          // Limpia formulario y actualiza cache local.
          this.form = { clientName: '', clientEmail: '', date: '', time: '', serviceId: '' };
          this.successMessage = 'Cita registrada correctamente.';
          this.allAppointments = this.sortAppointments([...this.allAppointments, appointment]);
          this.applyFilter();
        },
        error: (error) => {
          this.errorMessage = error?.error?.message ?? 'No fue posible registrar la cita.';
        }
      });
  }

  // Shortcut UI para pasar una cita a DONE.
  protected markAsDone(appointmentId: number): void {
    this.updateStatus(appointmentId, 'DONE');
  }

  // Cancela cita desde la UI.
  protected cancelAppointment(appointmentId: number): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.api.cancelAppointment(appointmentId).subscribe({
      next: (updatedAppointment) => {
        this.successMessage = 'Cita cancelada correctamente.';
        this.replaceAppointment(updatedAppointment);
      },
      error: (error) => {
        this.errorMessage = error?.error?.message ?? 'No fue posible cancelar la cita.';
      }
    });
  }

  // Traduce estado tecnico a etiqueta legible.
  protected statusLabel(status: AppointmentStatus): string {
    if (status === 'PENDING') return 'Pendiente';
    if (status === 'DONE') return 'Atendida';
    return 'Cancelada';
  }

  // TrackBy para evitar rerender completo de la lista.
  protected trackByAppointmentId(_: number, appointment: Appointment): number {
    return appointment.id;
  }

  // Actualiza estado de una cita y sincroniza cache local.
  private updateStatus(appointmentId: number, status: AppointmentStatus): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.api.updateAppointmentStatus(appointmentId, status).subscribe({
      next: (updatedAppointment) => {
        this.successMessage = 'Estado actualizado correctamente.';
        this.replaceAppointment(updatedAppointment);
      },
      error: (error) => {
        this.errorMessage = error?.error?.message ?? 'No fue posible actualizar el estado.';
      }
    });
  }

  // Reemplaza una cita puntual dentro del cache local.
  private replaceAppointment(updatedAppointment: Appointment): void {
    this.allAppointments = this.sortAppointments(
      this.allAppointments.map((appointment) => appointment.id === updatedAppointment.id ? updatedAppointment : appointment)
    );
    this.applyFilter();
  }

  // Aplica filtro actual sobre cache local.
  private applyFilter(): void {
    if (this.selectedStatus === 'ALL') {
      this.appointments = this.allAppointments;
      return;
    }

    this.appointments = this.allAppointments.filter((appointment) => appointment.status === this.selectedStatus);
  }

  // Mantiene orden cronologico en la lista.
  private sortAppointments(appointments: Appointment[]): Appointment[] {
    return appointments.sort((a, b) => {
      const first = `${a.date}T${a.time}`;
      const second = `${b.date}T${b.time}`;
      return first.localeCompare(second);
    });
  }
}
