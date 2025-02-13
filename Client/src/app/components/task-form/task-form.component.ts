// Angular core imports for component creation and data binding
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
// PrimeNG UI component imports
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextarea } from 'primeng/inputtextarea';
import { ButtonModule } from 'primeng/button';

/**
 * TaskFormComponent provides a reusable form for creating/editing tasks
 * Uses PrimeNG components for form inputs and styling
 */
@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css'],
  standalone: true,
  imports: [
    FormsModule,
    DropdownModule,
    InputTextModule,
    InputTextModule, // Note: InputTextModule is imported twice
    ButtonModule 
  ]
})
export class TaskFormComponent {
  // Input property to receive task data from parent component
  @Input() task: any = {};
  
  // Output event emitter to notify parent component when task is saved
  @Output() onSave = new EventEmitter<any>(); 

  /**
   * Emits the current task data to parent component when save is triggered
   */
  save() {
    this.onSave.emit(this.task);
  }
}