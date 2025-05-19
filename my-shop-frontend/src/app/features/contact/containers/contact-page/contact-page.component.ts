import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ContactFormComponent } from '../../components/contact-form/contact-form.component';

@Component({
  selector: 'app-contact-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ContactFormComponent],
  templateUrl: './contact-page.component.html',
})
export class ContactPageContainer {
  submitted = false;

  showDialog = false;

  /** Appelée depuis le form dumb en cas de soumission valide */
  onSubmitSuccess(): void {
    this.showDialog = true;
  }

  /** Ferme le modal */
  closeDialog(): void {
    this.showDialog = false;
  }
}