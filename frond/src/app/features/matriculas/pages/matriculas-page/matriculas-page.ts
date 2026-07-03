import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Enrollment, EnrollmentDetail } from '../../../../core/models/enrollment.model';
import { Student } from '../../../../core/models/student.model';
import { EnrollmentService } from '../../../../core/services/enrollment.service';
import { StudentService } from '../../../../core/services/student.service';

@Component({
  selector: 'app-matriculas-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './matriculas-page.html',
  styleUrl: './matriculas-page.css'
})
export class MatriculasPage implements OnInit {
  matriculas: Enrollment[] = [];
  estudiantes: Student[] = [];
  matriculaFormulario: Enrollment = this.crearFormularioVacio();
  matriculaEditandoId: number | null = null;
  detalleSeleccionado: EnrollmentDetail | null = null;
  cargando = false;
  mensaje = '';
  error = '';

  constructor(
    private readonly enrollmentService: EnrollmentService,
    private readonly studentService: StudentService
  ) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.cargarMatriculas();
    this.studentService.listar().subscribe({
      next: (estudiantes) => {
        this.estudiantes = estudiantes;
      },
      error: () => {
        this.error = 'No se pudo cargar estudiantes para seleccionar.';
      }
    });
  }

  cargarMatriculas(): void {
    this.cargando = true;
    this.error = '';

    this.enrollmentService.listar().subscribe({
      next: (matriculas) => {
        this.matriculas = matriculas;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la lista de matriculas.';
        this.cargando = false;
      }
    });
  }

  guardarMatricula(): void {
    this.mensaje = '';
    this.error = '';

    const operacion = this.matriculaEditandoId
      ? this.enrollmentService.actualizar(this.matriculaEditandoId, this.matriculaFormulario)
      : this.enrollmentService.registrar(this.matriculaFormulario);

    operacion.subscribe({
      next: () => {
        this.mensaje = this.matriculaEditandoId ? 'Matricula actualizada correctamente.' : 'Matricula registrada correctamente.';
        this.cancelarEdicion();
        this.cargarMatriculas();
      },
      error: () => {
        this.error = 'No se pudo guardar la matricula. Verifica que el estudiante exista.';
      }
    });
  }

  editarMatricula(enrollment: Enrollment): void {
    this.matriculaEditandoId = enrollment.id ?? null;
    this.matriculaFormulario = { ...enrollment };
    this.detalleSeleccionado = null;
    this.mensaje = '';
    this.error = '';
  }

  verDetalle(enrollment: Enrollment): void {
    if (!enrollment.id) {
      return;
    }

    this.enrollmentService.obtenerDetalle(enrollment.id).subscribe({
      next: (detalle) => {
        this.detalleSeleccionado = detalle;
      },
      error: () => {
        this.error = 'No se pudo consultar el detalle de la matricula.';
      }
    });
  }

  eliminarMatricula(enrollment: Enrollment): void {
    if (!enrollment.id) {
      return;
    }

    this.enrollmentService.eliminar(enrollment.id).subscribe({
      next: () => {
        this.mensaje = 'Matricula eliminada correctamente.';
        this.detalleSeleccionado = null;
        this.cargarMatriculas();
      },
      error: () => {
        this.error = 'No se pudo eliminar la matricula.';
      }
    });
  }

  cancelarEdicion(): void {
    this.matriculaEditandoId = null;
    this.matriculaFormulario = this.crearFormularioVacio();
  }

  obtenerNombreEstudiante(studentId: number): string {
    const student = this.estudiantes.find((item) => item.id === Number(studentId));
    return student ? `${student.firstName} ${student.lastName}` : `Estudiante ${studentId}`;
  }

  private crearFormularioVacio(): Enrollment {
    return {
      studentId: 0,
      courseName: '',
      enrollmentDate: new Date().toISOString().slice(0, 10),
      status: 'ACTIVA'
    };
  }
}
