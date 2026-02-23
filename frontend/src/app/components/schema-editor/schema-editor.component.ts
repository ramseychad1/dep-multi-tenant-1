import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export interface SchemaField {
  name: string;
  label: string;
  type: string;
  required: boolean;
  options?: string[];
  placeholder?: string;
}

export interface SchemaSection {
  title: string;
  fields: SchemaField[];
}

export interface FormSchema {
  sections: SchemaSection[];
}

@Component({
  selector: 'app-schema-editor',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './schema-editor.component.html',
  styleUrls: ['./schema-editor.component.css']
})
export class SchemaEditorComponent {
  @Input() schema: FormSchema = { sections: [] };
  @Output() schemaChange = new EventEmitter<FormSchema>();
  @Output() next = new EventEmitter<FormSchema>();
  @Output() back = new EventEmitter<void>();

  fieldTypes = ['text', 'email', 'phone', 'date', 'select', 'checkbox', 'radio', 'textarea', 'number', 'ssn', 'address'];

  addField(sectionIndex: number): void {
    this.schema.sections[sectionIndex].fields.push({
      name: 'newField',
      label: 'New Field',
      type: 'text',
      required: false,
      placeholder: ''
    });
    this.emitChange();
  }

  removeField(sectionIndex: number, fieldIndex: number): void {
    this.schema.sections[sectionIndex].fields.splice(fieldIndex, 1);
    this.emitChange();
  }

  addSection(): void {
    this.schema.sections.push({
      title: 'New Section',
      fields: []
    });
    this.emitChange();
  }

  removeSection(sectionIndex: number): void {
    this.schema.sections.splice(sectionIndex, 1);
    this.emitChange();
  }

  moveFieldUp(sectionIndex: number, fieldIndex: number): void {
    if (fieldIndex > 0) {
      const fields = this.schema.sections[sectionIndex].fields;
      [fields[fieldIndex - 1], fields[fieldIndex]] = [fields[fieldIndex], fields[fieldIndex - 1]];
      this.emitChange();
    }
  }

  moveFieldDown(sectionIndex: number, fieldIndex: number): void {
    const fields = this.schema.sections[sectionIndex].fields;
    if (fieldIndex < fields.length - 1) {
      [fields[fieldIndex], fields[fieldIndex + 1]] = [fields[fieldIndex + 1], fields[fieldIndex]];
      this.emitChange();
    }
  }

  onNext(): void {
    this.next.emit(this.schema);
  }

  onBack(): void {
    this.back.emit();
  }

  private emitChange(): void {
    this.schemaChange.emit(this.schema);
  }
}
