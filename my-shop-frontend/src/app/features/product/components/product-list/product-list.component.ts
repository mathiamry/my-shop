import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Product } from '../../models/product.dto';
import { ProductCardComponent } from '../product-card/product-card.component';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [ProductCardComponent, NgFor, NgIf],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent {
  @Input() products: Product[] = [];
  @Output() addToCart = new EventEmitter<Product>();
  @Output() increment = new EventEmitter<Product>();
  @Output() decrement = new EventEmitter<Product>();
  add(p: Product){
    this.addToCart.emit(p)
  }

  
  onIncrement(p: Product): void {
    this.increment.emit(p);
  }

  onDecrement(p: Product): void {
    this.decrement.emit(p);
  }
}
