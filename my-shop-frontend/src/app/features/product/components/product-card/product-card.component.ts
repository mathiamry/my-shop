// src/app/features/products/components/product-card/product-card.component.ts
import { Component, Input, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { CartService } from '../../../../core/services/cart.service';
import { Product } from '../../models/product.dto';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports:[NgIf],
  templateUrl: './product-card.component.html',
})
export class ProductCardComponent implements OnInit, OnDestroy {
  @Input() product!: Product;
  @Input() imageUrl!: string;

  /** Quantité actuelle dans le panier */
  cartQuantity = 0;
  private sub!: Subscription;

  /** Actions émises vers le container */
  @Output() addToCart = new EventEmitter<Product>();
  @Output() increment = new EventEmitter<Product>();
  @Output() decrement = new EventEmitter<Product>();

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    // Souscription pour recevoir les changements du panier
    this.sub = this.cartService.items$.subscribe(items => {
      const item = items.find(i => i.product.id === this.product.id);
      this.cartQuantity = item ? item.quantity : 0;
    });
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  /** Émission de l’ajout d’un produit */
  onAddToCart(): void {
    this.addToCart.emit(this.product);
  }

  /** Émission de l’incrémentation */
  onIncrement(): void {
    this.increment.emit(this.product);
  }

  /** Émission de la décrémentation */
  onDecrement(): void {
    console.log("decrement");
    
    this.decrement.emit(this.product);
  }
}