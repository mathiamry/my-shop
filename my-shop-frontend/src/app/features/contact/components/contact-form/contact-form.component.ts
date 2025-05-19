import { NgIf } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './contact-form.component.html'
})
export class ContactFormComponent implements OnInit{
  @Output() success = new EventEmitter<void>();
  form! : FormGroup;
  
  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]]
    });
  
  }
  onSubmit(): void {
    if (this.form.valid) {
      this.success.emit();
    } else {
      this.form.markAllAsTouched();
    }
  }
}