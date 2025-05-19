import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CartItem } from '../../models/cart-item.dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart-item.component.html',
  styleUrl: './cart-item.component.scss'
})
export class CartItemComponent {
  @Input() item!: CartItem;
  @Output() remove    = new EventEmitter<CartItem>();
  @Output() increment = new EventEmitter<CartItem>();
  @Output() decrement = new EventEmitter<CartItem>();
}
