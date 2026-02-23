import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { EnrollmentProgramService, ProgramListResponse } from '../../services/enrollment-program.service';
import { EnrollmentProgram } from '../../services/contentful.service';

@Component({
  selector: 'app-program-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './program-list.component.html',
  styleUrls: ['./program-list.component.css']
})
export class ProgramListComponent implements OnInit {
  programs: EnrollmentProgram[] = [];
  loading = true;
  error = '';

  constructor(
    private programService: EnrollmentProgramService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPrograms();
  }

  loadPrograms(): void {
    this.loading = true;
    this.error = '';
    this.programService.getPrograms().subscribe({
      next: (response: ProgramListResponse) => {
        this.programs = response.data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load enrollment programs.';
        this.loading = false;
      }
    });
  }

  getWorkflowLabel(program: EnrollmentProgram): string {
    if (!program.workflow_types || program.workflow_types.length === 0) {
      return 'Enrollment';
    }
    return program.workflow_types
      .map(t => t.charAt(0).toUpperCase() + t.slice(1))
      .join(' & ') + ' Enrollment';
  }

  getStatusClass(status: string): string {
    switch (status?.toLowerCase()) {
      case 'active': return 'badge-active';
      case 'inactive': return 'badge-inactive';
      default: return 'badge-pending';
    }
  }

  toggleStatus(program: EnrollmentProgram): void {
    const newStatus = program.status === 'Active' ? 'Inactive' : 'Active';
    this.programService.updateStatus(program.id, newStatus).subscribe({
      next: () => {
        program.status = newStatus;
      },
      error: () => {
        this.error = 'Failed to update program status.';
      }
    });
  }

  editProgram(program: EnrollmentProgram): void {
    this.router.navigate(['/admin/wizard', program.id]);
  }

  viewProgram(program: EnrollmentProgram): void {
    window.open(`/enroll/${program.id}`, '_blank');
  }

  createProgram(): void {
    this.router.navigate(['/admin/wizard']);
  }
}
