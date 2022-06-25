from django.contrib import admin
from django.urls import include, path

from api.views.address import AddressViewList, AddressViewDetail
from api.views.client import ClientViewList, ClientViewDetail
from api.views.delivery_man import (
    DeliveryManViewList,
    DeliveryManViewDetail,
    DeliveryManViewHighestRated
)
from api.views.order import (
    OrderViewList,
    OrderViewDetail,
    # OrderModify,
    OrderMostExpensive,
    OrderViewModifyStatus,
    OrderAddItem,
    OrderAddRating
)
from api.views.product import (
    ProductViewList,
    ProductViewDetail,
    ProductViewAverage,
    ProductViewHistoricValues
)
from api.views.product_type import ProductTypeViewList, ProductTypeViewDetail
from api.views.supplier import (
    SupplierViewList,
    SupplierViewDetail,
    SupplierViewSearchByType,
    SupplierViewProducts,
    SupplierViewAllTypes,
    SupplierViewAllWithLowestRating,
    SupplierViewAllMostOrders,
    SupplierViewOrderWithMostProducts
)
from api.views.supplier_type import SupplierTypeViewList, SupplierTypeViewDetail

urlpatterns = [
    path(r'address/', AddressViewList.as_view()),
    path(r'address/<str:number>', AddressViewDetail.as_view()),
    path(r'client/', ClientViewList.as_view()),
    path(r'client/<str:number>', ClientViewDetail.as_view()),
    path(r'delivery-man/', DeliveryManViewList.as_view()),
    path(r'delivery-man/highest-rated', DeliveryManViewHighestRated.as_view()),
    path(r'delivery-man/<str:number>', DeliveryManViewDetail.as_view()),
    path(r'order/', OrderViewList.as_view()),
    path(r'order/most-expensive', OrderMostExpensive.as_view()),
    path(r'order/<str:number>', OrderViewDetail.as_view()),
    path(r'order/<str:number>/add-item', OrderAddItem.as_view()),
    path(r'order/<str:number>/add-rating', OrderAddRating.as_view()),
    path(r'order/<str:number>/<slug:stat_update>', OrderViewModifyStatus.as_view()),
    # path(r'order/<int:number>/add-product/<int:prod_number>', OrderModify.as_view()),
    path(r'product/', ProductViewList.as_view()),
    path(r'product/average-price', ProductViewAverage.as_view()),
    path(r'product/<str:number>', ProductViewDetail.as_view()),
    path(r'product/<str:number>/historic-prices', ProductViewHistoricValues.as_view()),
    path(r'product-type/', ProductTypeViewList.as_view()),
    path(r'product-type/<str:number>', ProductTypeViewDetail.as_view()),
    path(r'supplier/', SupplierViewList.as_view()),
    path(r'supplier/by-type', SupplierViewSearchByType.as_view()),
    path(r'supplier/all-types', SupplierViewAllTypes.as_view()),
    path(r'supplier/highest-order-count', SupplierViewAllMostOrders.as_view()),
    path(r'supplier/with-lowest-rating', SupplierViewAllWithLowestRating.as_view()),
    path(r'supplier/<str:number>', SupplierViewDetail.as_view()),
    path(r'supplier/<str:supplier_id>/products', SupplierViewProducts.as_view()),
    path(r'supplier/<str:supplier_id>/order-with-most-products', SupplierViewOrderWithMostProducts.as_view()),
    path(r'supplier-type/', SupplierTypeViewList.as_view()),
    path(r'supplier-type/<str:number>', SupplierTypeViewDetail.as_view()),
]
