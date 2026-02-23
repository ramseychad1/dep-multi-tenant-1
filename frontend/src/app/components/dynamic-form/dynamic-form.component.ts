import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ContentfulService, EnrollmentProgram } from '../../services/contentful.service';
import { FormSchema, SchemaField, SchemaSection } from '../schema-editor/schema-editor.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dynamic-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.css']
})
export class DynamicFormComponent implements OnInit {
  program: EnrollmentProgram | null = null;
  schema: FormSchema = { sections: [] };
  formData: Record<string, any> = {};
  loading = true;
  error = '';
  submitted = false;
  submitting = false;

  constructor(
    private route: ActivatedRoute,
    private contentfulService: ContentfulService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    const programId = this.route.snapshot.paramMap.get('programId');
    if (programId) {
      this.contentfulService.getPublicEnrollmentProgram(programId).subscribe({
        next: (program) => {
          this.program = program;
          this.schema = program.form_schema || { sections: [] };
          this.initFormData();
          this.loading = false;
        },
        error: () => {
          this.error = 'Failed to load enrollment form. Please try again later.';
          this.loading = false;
        }
      });
    } else {
      this.error = 'No program ID provided.';
      this.loading = false;
    }
  }

  onSubmit(): void {
    if (!this.program) return;

    this.submitting = true;
    this.http.post('http://localhost:8080/api/v1/form-submissions', {
      enrollment_program_id: this.program.id,
      submission_data: this.formData
    }).subscribe({
      next: () => {
        this.submitted = true;
        this.submitting = false;
      },
      error: () => {
        this.error = 'Failed to submit form. Please try again.';
        this.submitting = false;
      }
    });
  }

  getInputType(field: SchemaField): string {
    switch (field.type) {
      case 'email': return 'email';
      case 'phone': return 'tel';
      case 'date': return 'date';
      case 'number': return 'number';
      default: return 'text';
    }
  }

  private initFormData(): void {
    for (const section of this.schema.sections) {
      for (const field of section.fields) {
        this.formData[field.name] = field.type === 'checkbox' ? false : '';
      }
    }
  }
}
