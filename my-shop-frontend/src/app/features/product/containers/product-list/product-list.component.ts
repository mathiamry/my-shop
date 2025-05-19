import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ProductListComponent } from '../../components/product-list/product-list.component';
import { CartService } from '../../../../core/services/cart.service';
import { ProductService } from '../../../../core/services/product.service';
import { Product } from '../../models/product.dto';

@Component({
  selector: 'app-product-list-container',
  standalone: true,
  imports: [ CommonModule, ReactiveFormsModule, ProductListComponent ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListContainer implements OnInit {
  // Toutes les données
  products = signal<Product[]>([]);

  // Filtre et pagination
  searchTerm = signal('');
  page       = signal(0);
  pageSize   = 5; 

  // Liste filtrée
  filtered = computed(() => {
    const term = this.searchTerm().toLowerCase();
    return this.products().filter(p =>
      p.name.toLowerCase().includes(term)
      || p.category.toLowerCase().includes(term)
      || p.code.toLowerCase().includes(term)
    );
  });

  // Liste paginée
  paginated = computed(() => {
    const start = this.page() * this.pageSize;
    return this.filtered().slice(start, start + this.pageSize);
  });

  // Nombre de pages
  totalPages = computed(() =>
    Math.ceil(this.filtered().length / this.pageSize)
  );

  constructor(
    private productSvc: ProductService,
    private cartSvc:    CartService
  ) {}

  ngOnInit(): void {
    this.productSvc.getAll().subscribe(list => this.products.set(list));
  }

  onSearch(term: string) {
    this.searchTerm.set(term);
    this.page.set(0);
  }

  onPageChange(newPage: number) {
    this.page.set(newPage);
  }

  onAdd(p: Product) {
    this.cartSvc.add(p);
  }


  onIncrement(p: Product): void {
    this.cartSvc.add(p);
  }

  onDecrement(p: Product): void {
    console.log("on decrement", p);
    
    this.cartSvc.decrement(p);
  }
}
