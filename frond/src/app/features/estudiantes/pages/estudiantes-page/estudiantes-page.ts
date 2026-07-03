import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Student } from '../../../../core/models/student.model';
import { StudentService } from '../../../../core/services/student.service';

@Component({
  selector: 'app-estudiantes-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './estudiantes-page.html',
  styleUrl: './estudiantes-page.css'
})
export class EstudiantesPage implements OnInit {
  estudiantes: Student[] = [];
  estudianteFormulario: Student = this.crearFormularioVacio();
  estudianteEditandoId: number | null = null;
  cargando = false;
  mensaje = '';
  error = '';

  constructor(private readonly studentService: StudentService) {}

  ngOnInit(): void {
    this.cargarEstudiantes();
  }

  cargarEstudiantes(): void {
    this.cargando = true;
    this.error = '';

    this.studentService.listar().subscribe({
      next: (estudiantes) => {
        this.estudiantes = estudiantes;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la lista de estudiantes.';
        this.cargando = false;
      }
    });
  }

  guardarEstudiante(): void {
    this.mensaje = '';
    this.error = '';

    const operacion = this.estudianteEditandoId
      ? this.studentService.actualizar(this.estudianteEditandoId, this.estudianteFormulario)
      : this.studentService.registrar(this.estudianteFormulario);

    operacion.subscribe({
      next: () => {
        this.mensaje = this.estudianteEditandoId ? 'Estudiante actualizado correctamente.' : 'Estudiante registrado correctamente.';
        this.cancelarEdicion();
        this.cargarEstudiantes();
      },
      error: () => {
        this.error = 'No se pudo guardar el estudiante. Revisa el DNI y los datos obligatorios.';
      }
    });
  }

  editarEstudiante(student: Student): void {
    this.estudianteEditandoId = student.id ?? null;
    this.estudianteFormulario = { ...student };
    this.mensaje = '';
    this.error = '';
  }

  eliminarEstudiante(student: Student): void {
    if (!student.id) {
      return;
    }

    this.studentService.eliminar(student.id).subscribe({
      next: () => {
        this.mensaje = 'Estudiante eliminado correctamente.';
        this.cargarEstudiantes();
      },
      error: () => {
        this.error = 'No se pudo eliminar el estudiante.';
      }
    });
  }

  cancelarEdicion(): void {
    this.estudianteEditandoId = null;
    this.estudianteFormulario = this.crearFormularioVacio();
  }

  private crearFormularioVacio(): Student {
    return {
      dni: '',
      firstName: '',
      lastName: '',
      promotion: '',
      date: new Date().toISOString().slice(0, 10)
    };
  }
}
