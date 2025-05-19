// src/app/core/services/cart.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Product } from '../../features/product/models/product.dto';

/** Interface représentant un item dans le panier */
export interface CartItem {
  product: Product;
  quantity: number;
}

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly itemsSubject = new BehaviorSubject<CartItem[]>([]);

  /** Observable émettant la liste des items du panier */
  readonly items$: Observable<CartItem[]> = this.itemsSubject.asObservable();

  /** Observable émettant le nombre total de produits dans le panier */
  readonly count$: Observable<number> = this.items$.pipe(
    map(items => items.reduce((sum, item) => sum + item.quantity, 0))
  );

  /** Ajoute un produit au panier (incrémente la quantité si déjà présent) */
  add(product: Product): void {
    const items = this.itemsSubject.getValue();
    const idx = items.findIndex(i => i.product.id === product.id);
    if (idx > -1) {
      items[idx].quantity += 1;
    } else {
      items.push({ product, quantity: 1 });
    }
    this.itemsSubject.next([...items]);
  }

  /** Supprime un produit du panier (entièrement) */
  remove(product: Product): void {
    const items = this.itemsSubject.getValue().filter(i => i.product.id !== product.id);
    this.itemsSubject.next([...items]);
  }

  /** Diminue la quantité d'un produit (ou le retire si la quantité arrive à 0) */
  decrement(product: Product): void {
    const items = this.itemsSubject.getValue();
    const idx = items.findIndex(i => i.product.id === product.id);
    if (idx > -1) {
      if (items[idx].quantity > 1) {
        items[idx].quantity -= 1;
      } else {
        items.splice(idx, 1);
      }
    }
    this.itemsSubject.next([...items]);
  }

  /** Vide entièrement le panier */
  clear(): void {
    this.itemsSubject.next([]);
  }
}
