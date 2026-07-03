import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enrollment, EnrollmentDetail } from '../models/enrollment.model';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {
  private readonly apiUrl = 'http://localhost:8095/v1/api/enrollment';

  constructor(private readonly http: HttpClient) {}

  listar(): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(`${this.apiUrl}/list`);
  }

  obtenerDetalle(id: number): Observable<EnrollmentDetail> {
    return this.http.get<EnrollmentDetail>(`${this.apiUrl}/detail/${id}`);
  }

  registrar(enrollment: Enrollment): Observable<Enrollment> {
    return this.http.post<Enrollment>(`${this.apiUrl}/register`, enrollment);
  }

  actualizar(id: number, enrollment: Enrollment): Observable<Enrollment> {
    return this.http.put<Enrollment>(`${this.apiUrl}/update/${id}`, enrollment);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
