import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CartItem } from '../../models/cart-item.dto';
import { CommonModule } from '@angular/common';
import { CartItemComponent } from '../cart-item/cart-item.component';

@Component({
  selector: 'app-cart-list',
  standalone: true,
  imports: [CommonModule, CartItemComponent],
  templateUrl: './cart-list.component.html',
  styleUrl: './cart-list.component.scss'
})
export class CartListComponent {
  @Input() items: CartItem[] = [];
  @Output() remove    = new EventEmitter<CartItem>();
  @Output() increment = new EventEmitter<CartItem>();
  @Output() decrement = new EventEmitter<CartItem>();
}
