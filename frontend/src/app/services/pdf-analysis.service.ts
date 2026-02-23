import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PdfAnalysisService {
  private readonly apiUrl = 'http://localhost:8080/api/v1/pdf-analysis';

  constructor(private http: HttpClient) {}

  analyzePdf(file: File, aiProvider: string): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('aiProvider', aiProvider);
    return this.http.post(this.apiUrl, formData);
  }
}
