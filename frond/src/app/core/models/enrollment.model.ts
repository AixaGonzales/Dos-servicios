import { Student } from './student.model';

export interface Enrollment {
  id?: number;
  studentId: number;
  courseName: string;
  enrollmentDate: string;
  status: string;
}

export interface EnrollmentDetail {
  enrollment: Enrollment;
  student: Student;
}
