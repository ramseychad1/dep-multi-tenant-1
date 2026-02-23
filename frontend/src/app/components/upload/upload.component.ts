import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent {
  @Output() fileSelected = new EventEmitter<{ file: File; aiProvider: string }>();

  selectedFile: File | null = null;
  aiProvider = 'claude';
  isDragging = false;

  onFileSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.setFile(input.files[0]);
    }
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = true;
  }

  onDragLeave(): void {
    this.isDragging = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = false;
    if (event.dataTransfer?.files && event.dataTransfer.files.length > 0) {
      this.setFile(event.dataTransfer.files[0]);
    }
  }

  triggerFileInput(fileInput: HTMLInputElement): void {
    fileInput.click();
  }

  onNext(): void {
    if (this.selectedFile) {
      this.fileSelected.emit({ file: this.selectedFile, aiProvider: this.aiProvider });
    }
  }

  getFileSize(): string {
    if (!this.selectedFile) return '';
    return (this.selectedFile.size / 1024).toFixed(2) + ' KB';
  }

  private setFile(file: File): void {
    if (file.type === 'application/pdf') {
      this.selectedFile = file;
    }
  }
}
