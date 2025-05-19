import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { CartListComponent } from '../../components/cart-list/cart-list.component';
import { CartService } from '../../../../core/services/cart.service';
import { CartItem } from '../../models/cart-item.dto';
import { Observable, map } from 'rxjs';
import { Router } from 'express';

@Component({
  selector: 'app-cart-page',
  standalone: true,
  imports: [ CommonModule, CartListComponent ],

  templateUrl: './cart-page.component.html',
  styleUrl: './cart-page.component.scss'
})
export class CartPageComponent {
    /** Flux des items du panier */
  items$!: Observable<CartItem[] | null>;

  /** Flux du total du panier */
  total$!: Observable<number>;

  constructor(
    private cartService: CartService,
  ) {}

  ngOnInit(): void {
    // Initialisation des observables après que cartService soit disponible
    this.items$ = this.cartService.items$;
    this.total$ = this.cartService.items$.pipe(
      map(items => items.reduce((sum, i) => sum + i.product.price * i.quantity, 0))
    );
  }

  /** Supprimer un item complètement */
  onRemove(item: CartItem): void {
    this.cartService.remove(item.product);
  }

  /** Incrémenter la quantité */
  onIncrement(item: CartItem): void {
    this.cartService.add(item.product);
  }

  /** Décrémenter la quantité */
  onDecrement(item: CartItem): void {
    this.cartService.decrement(item.product);
  }

  
}
