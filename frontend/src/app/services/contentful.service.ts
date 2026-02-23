import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { FormSchema } from '../components/schema-editor/schema-editor.component';

export interface EnrollmentProgram {
  id: string;
  client_name: string;
  program_name: string;
  program_description?: string;
  workflow_types?: string[];
  form_schema?: FormSchema;
  logo_url?: string;
  primary_color?: string;
  secondary_color?: string;
  accent_color?: string;
  header_background_color?: string;
  text_color?: string;
  font_preference?: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class ContentfulService {
  private readonly apiUrl = 'http://localhost:8080/api/v1/enrollment-programs';

  constructor(private http: HttpClient) {}

  getEnrollmentProgram(programId: string): Observable<EnrollmentProgram> {
    return this.http.get<EnrollmentProgram>(`${this.apiUrl}/${programId}`);
  }

  getPublicEnrollmentProgram(programId: string): Observable<EnrollmentProgram> {
    return this.http.get<EnrollmentProgram>(`${this.apiUrl}/${programId}/public`);
  }

  getFormSchema(programId: string): Observable<FormSchema> {
    return this.getPublicEnrollmentProgram(programId).pipe(
      map(program => program.form_schema || { sections: [] })
    );
  }
}
