import { Routes } from '@angular/router';
import { AppLayout } from './layout/components/app-layout/app-layout';
import { EstudiantesPage } from './features/estudiantes/pages/estudiantes-page/estudiantes-page';
import { MatriculasPage } from './features/matriculas/pages/matriculas-page/matriculas-page';

export const routes: Routes = [
  {
    path: '',
    component: AppLayout,
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'estudiantes' },
      { path: 'estudiantes', component: EstudiantesPage },
      { path: 'matriculas', component: MatriculasPage }
    ]
  },
  { path: '**', redirectTo: 'estudiantes' }
];
