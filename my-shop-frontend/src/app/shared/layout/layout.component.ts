import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Router } from 'express';
import { Observable } from 'rxjs';
import { CartService } from '../../core/services/cart.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, NgIf],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
 /** Observable du nombre d'articles dans le panier */
 count$: Observable<number>;

 constructor(private cartService: CartService) {
   this.count$ = this.cartService.count$;
 }
}
