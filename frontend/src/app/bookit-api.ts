import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { timeout } from 'rxjs/operators';
import { Appointment, AppointmentStatus, CreateAppointmentPayload, ServiceItem } from './bookit-models';

@Injectable({
  providedIn: 'root'
})
export class BookitApiService {
  // Cliente HTTP inyectado por Angular.
  private readonly http = inject(HttpClient);
  // Prefijo comun para todas las rutas de API (proxy Nginx -> backend).
  private readonly baseUrl = '/api';
  // Tiempo maximo de espera por peticion.
  private readonly requestTimeoutMs = 4000;

  // Obtiene el catalogo de servicios.
  getServices(): Observable<ServiceItem[]> {
    return this.http.get<ServiceItem[]>(`${this.baseUrl}/services`)
      .pipe(timeout(this.requestTimeoutMs));
  }

  // Obtiene citas; puede incluir filtro por estado.
  getAppointments(status: AppointmentStatus | null): Observable<Appointment[]> {
    const params = status ? new HttpParams().set('status', status) : undefined;
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointments`, { params })
      .pipe(timeout(this.requestTimeoutMs));
  }

  // Crea una nueva cita.
  createAppointment(payload: CreateAppointmentPayload): Observable<Appointment> {
    return this.http.post<Appointment>(`${this.baseUrl}/appointments`, payload)
      .pipe(timeout(this.requestTimeoutMs));
  }

  // Cambia estado de una cita existente.
  updateAppointmentStatus(id: number, status: AppointmentStatus): Observable<Appointment> {
    return this.http.patch<Appointment>(`${this.baseUrl}/appointments/${id}/status`, { status })
      .pipe(timeout(this.requestTimeoutMs));
  }

  // Cancela una cita existente.
  cancelAppointment(id: number): Observable<Appointment> {
    return this.http.delete<Appointment>(`${this.baseUrl}/appointments/${id}`)
      .pipe(timeout(this.requestTimeoutMs));
  }
}
