import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { UploadComponent } from '../upload/upload.component';
import { SchemaEditorComponent, FormSchema } from '../schema-editor/schema-editor.component';
import { PdfAnalysisService } from '../../services/pdf-analysis.service';
import { EnrollmentProgramService } from '../../services/enrollment-program.service';

@Component({
  selector: 'app-wizard',
  standalone: true,
  imports: [CommonModule, UploadComponent, SchemaEditorComponent],
  templateUrl: './wizard.component.html',
  styleUrls: ['./wizard.component.css']
})
export class WizardComponent implements OnInit {
  currentStep = 1;
  steps = ['Upload', 'Review Schema', 'Branding', 'Program Details'];

  uploadedFile: File | null = null;
  aiProvider = 'claude';
  generatedSchema: FormSchema = { sections: [] };
  isAnalyzing = false;
  analysisError = '';

  programId: string | null = null;
  isEditMode = false;
  loadingProgram = false;

  constructor(
    private pdfAnalysisService: PdfAnalysisService,
    private programService: EnrollmentProgramService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.programId = id;
        this.isEditMode = true;
        this.loadExistingProgram(id);
      }
    });
  }

  loadExistingProgram(id: string): void {
    this.loadingProgram = true;
    this.programService.getProgram(id).subscribe({
      next: (program) => {
        if (program.form_schema) {
          this.generatedSchema = typeof program.form_schema === 'string'
            ? JSON.parse(program.form_schema)
            : program.form_schema;
          this.currentStep = 2;
        }
        this.loadingProgram = false;
      },
      error: () => {
        this.analysisError = 'Failed to load program data.';
        this.loadingProgram = false;
      }
    });
  }

  onFileUploaded(event: { file: File; aiProvider: string }): void {
    this.uploadedFile = event.file;
    this.aiProvider = event.aiProvider;
    this.isAnalyzing = true;
    this.analysisError = '';

    this.pdfAnalysisService.analyzePdf(event.file, event.aiProvider).subscribe({
      next: (schema) => {
        this.generatedSchema = typeof schema === 'string' ? JSON.parse(schema) : schema;
        this.isAnalyzing = false;
        this.currentStep = 2;
      },
      error: (err) => {
        console.error('PDF analysis error:', err);
        this.analysisError = 'Failed to analyze PDF. Please try again.';
        this.isAnalyzing = false;
      }
    });
  }

  onSchemaNext(schema: FormSchema): void {
    this.generatedSchema = schema;
    this.currentStep = 3;
  }

  onSchemaBack(): void {
    this.currentStep = 1;
  }

  goToStep(step: number): void {
    if (step <= this.currentStep) {
      this.currentStep = step;
    }
  }
}
