import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from '../models/student.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private readonly apiUrl = 'http://localhost:8090/v1/api/student';

  constructor(private readonly http: HttpClient) {}

  listar(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.apiUrl}/list`);
  }

  registrar(student: Student): Observable<Student> {
    return this.http.post<Student>(`${this.apiUrl}/register`, student);
  }

  actualizar(id: number, student: Student): Observable<Student> {
    return this.http.put<Student>(`${this.apiUrl}/update/${id}`, student);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
