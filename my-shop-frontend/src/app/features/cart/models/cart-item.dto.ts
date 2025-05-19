// src/app/shared/models/cart-item.model.ts

import { Product } from "../../product/models/product.dto";


export interface CartItem {
  product: Product;
  quantity: number;
}
