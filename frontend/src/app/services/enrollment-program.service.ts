import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { EnrollmentProgram } from './contentful.service';

export interface ProgramListResponse {
  data: EnrollmentProgram[];
  total: number;
  page: number;
  pageSize: number;
}

@Injectable({
  providedIn: 'root'
})
export class EnrollmentProgramService {
  private readonly apiUrl = 'http://localhost:8080/api/v1/enrollment-programs';

  constructor(private http: HttpClient) {}

  getPrograms(): Observable<ProgramListResponse> {
    return this.http.get<ProgramListResponse>(this.apiUrl);
  }

  getProgram(id: string): Observable<EnrollmentProgram> {
    return this.http.get<EnrollmentProgram>(`${this.apiUrl}/${id}`);
  }

  createProgram(program: Partial<EnrollmentProgram>): Observable<EnrollmentProgram> {
    return this.http.post<EnrollmentProgram>(this.apiUrl, program);
  }

  updateProgram(id: string, program: Partial<EnrollmentProgram>): Observable<EnrollmentProgram> {
    return this.http.put<EnrollmentProgram>(`${this.apiUrl}/${id}`, program);
  }

  updateStatus(id: string, status: string): Observable<EnrollmentProgram> {
    return this.http.patch<EnrollmentProgram>(`${this.apiUrl}/${id}/status`, { status });
  }

  deleteProgram(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
