// Estados permitidos para una cita.
export type AppointmentStatus = 'PENDING' | 'DONE' | 'CANCELLED';

// Servicio disponible para reservar.
export interface ServiceItem {
  id: number;
  name: string;
  description: string;
  durationMinutes: number;
}

// Cita mostrada en el frontend.
export interface Appointment {
  id: number;
  clientName: string;
  clientEmail: string;
  date: string;
  time: string;
  status: AppointmentStatus;
  service: ServiceItem;
}

// Payload requerido para crear cita.
export interface CreateAppointmentPayload {
  clientName: string;
  clientEmail: string;
  date: string;
  time: string;
  serviceId: number;
}
