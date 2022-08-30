function paginationComponent(currentPage){
    
    if(currentPage == null || currentPage == undefined) 
        currentPage = 0
    else
        currentPage -= 1
    
    const PAGE_ITEM_CNT = 5
    let start = (parseInt(currentPage / PAGE_ITEM_CNT) * PAGE_ITEM_CNT)
    let end = ((parseInt(currentPage / PAGE_ITEM_CNT) + 1) * PAGE_ITEM_CNT)

    let header = `
    <div class="ltn__pagination">
        <ul>
            <li><a href="/product?page=`+start+`"><i class="fas fa-angle-left"></i></a></li>`
    let html = ``
    let footer = `
            <li><a href="/product?page=`+(end+1)+`"><i class="fas fa-angle-right"></i></a></li>
        </ul>
    </div>`

    for(let pageCnt=start; pageCnt<end; pageCnt++){
        if(pageCnt == currentPage)
            html += `<li class="active"><a href="#">`+ (pageCnt+1) +`</a></li>`
        else
            html += `<li><a href="/product?page=`+(pageCnt+1)+`">`+ (pageCnt+1) + `</a></li>`
    }

    return header + html + footer      
}

function headerHTML(user){
    let userMenuHtml = ""

    if(user != null) {
        userMenuHtml = `
        <li><a href="/user/account">내 계정정보</a></li>
        <li><a href="/user/wishlist">찜 목록</a></li>
        <li><a href="/user/cart">장바구니</a></li>
        <li><a href="/user/signout">로그아웃</a></li>`
    } else {
        userMenuHtml = `
        <li><a href="/user/signin">로그인</a></li>
        <li><a href="/user/signup">회원가입</a></li>`
    }

    let html = `<header class="ltn__header-area ltn__header-3">       
        <!-- ltn__header-middle-area start -->
        <div class="ltn__header-middle-area">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <div class="site-logo">
                            <!-- mb-0 은 자식 선택자 CSS 로 변경 -->
                            <a href="/" class="d-flex align-items-center">
                                 <h3 class="ltn__secondary-color mb-0">K</h3><h3 class="mb-0">-Market</h3>
                            </a>
                        </div>
                    </div>
                    <div class="col header-contact-serarch-column d-none d-lg-block">
                        <div class="header-contact-search">
                            <!-- header-search-2 -->
                            <div class="header-search-2">
                                <form id="#123" method="get"  action="/product">
                                    <input type="text" name="search" value="" placeholder="검색할 키워드를 입력하세요"/>
                                    <button type="submit">
                                        <span><i class="icon-search"></i></span>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <!-- header-options -->
                        <div class="ltn__header-options">
                            <ul>
                                <li class="d-none---"> 
                                    <!-- user-menu -->
                                    <div class="ltn__drop-menu user-menu">
                                        <ul>`
    if(user == null)
        html += 
                                            `<span style="font-size:13px">로그인을 해주세요 </span>
                                            <li>
                                                <a href="#"><i class="fas fa-user-slash"></i></a>` 
    else{
        html += 
                                            `<span style="font-size:12px">
                                                안녕하세요,
                                                <strong style="font-size:14px">`+user.name+`</strong>님
                                            </span>
                                            <li>
                                                <a href="#"><i class="fas fa-user"></i></a>` 
    }
    
    html += `
                                                <ul id="user-menu-item">`
                                                + userMenuHtml + 
                                                `</ul>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <!-- mini-cart 2 -->
                                    <div class="mini-cart-icon mini-cart-icon-2">
                                        <a href="#ltn__utilize-cart-menu" class="ltn__utilize-toggle">
                                            <span class="mini-cart-icon">
                                                <i class="icon-shopping-cart"></i>
                                            </span>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ltn__header-middle-area end -->
        <!-- header-bottom-area start -->
        <div class="header-bottom-area ltn__header-sticky  ltn__sticky-bg-white--- ltn__sticky-bg-secondary ltn__secondary-bg section-bg-1 menu-color-white d-none d-lg-block">
            <div class="container">
                <div class="row">
                    <div class="col header-menu-column justify-content-center">
                        <div class="sticky-logo">
                            <div class="site-logo">
                                <a href="/" class="d-flex align-items-center">
                                    <h3 class="ltn__primary-color mb-0">K</h3><h3 class="text-color-white mb-0">-Market</h3>
                               </a>
                            </div>
                        </div>
                        <div class="header-menu header-menu-2">
                            <nav>
                                <div class="ltn__main-menu">
                                    <ul>
                                        <!-- <li class="menu_icon">
                                            <form id="#123" method="get"  action="/product">
                                                <input type="text" name="search" value="" placeholder="검색할 키워드를 입력하세요"/>
                                                <button type="submit">
                                                    <span><i class="icon-search"></i></span>
                                                </button>
                                            </form>
                                        </li> -->
                                        <li class="menu-icon"><a href="#">About</a>
                                            <ul>
                                                <li><a href="about.html">About</a></li>
                                                <li><a href="service.html">Services</a></li>
                                                <li><a href="service-details.html">Service Details</a></li>
                                                <li><a href="portfolio.html">Gallery</a></li>
                                                <li><a href="portfolio-2.html">Gallery - 02</a></li>
                                                <li><a href="portfolio-details.html">Gallery Details</a></li>
                                                <li><a href="team.html">Team</a></li>
                                                <li><a href="team-details.html">Team Details</a></li>
                                                <li><a href="faq.html">FAQ</a></li>
                                                <li><a href="locations.html">Google Map Locations</a></li>
                                            </ul>
                                        </li>
                                        <li class="menu-icon"><a href="#">Shop</a>
                                            <ul>
                                                <li><a href="shop.html">Shop</a></li>
                                                <li><a href="shop-grid.html">Shop Grid</a></li>
                                                <li><a href="shop-left-sidebar.html">Shop Left sidebar</a></li>
                                                <li><a href="shop-right-sidebar.html">Shop right sidebar</a></li>
                                                <li><a href="product-details.html">Shop details </a></li>
                                                <li><a href="#">Other Pages <span class="float-right">>></span></a>
                                                    <ul>
                                                        <li><a href="/user/cart">Cart</a></li>
                                                        <li><a href="/user/wishlist">Wishlist</a></li>
                                                        <li><a href="checkout.html">Checkout</a></li>
                                                        <li><a href="order-tracking.html">Order Tracking</a></li>
                                                        <li><a href="/user/account">My Account</a></li>
                                                        <li><a href="/user/signin">Sign in</a></li>
                                                        <li><a href="/user/signup">Register</a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </li> 
                                        <li class="menu-icon"><a href="#">카테고리</a>
                                            <ul class="mega-menu">
                                                <!-- 카테고리 반복 부분-->
                                                <li><a href="#">Inner Pages</a>
                                                    <ul>
                                                        <li><a href="portfolio.html">Gallery</a></li>
                                                        <li><a href="portfolio-2.html">Gallery - 02</a></li>
                                                        <li><a href="portfolio-details.html">Gallery Details</a></li>
                                                        <li><a href="team.html">Team</a></li>
                                                        <li><a href="team-details.html">Team Details</a></li>
                                                        <li><a href="faq.html">FAQ</a></li>
                                                    </ul>
                                                </li>
                                                <li><a href="shop.html"><img src="http://127.0.0.1/img/banner/menu-banner-1.png" alt="#"></a>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- header-bottom-area end -->
    </header>`

    return html
}

function shopDetailsInfoComponent(product){
    return `
        <div class="modal-product-info shop-details-info pl-0">
            <div class="product-ratting">
                <ul>
                    <li><a href="#"><i class="fas fa-star"></i></a></li>
                    <li><a href="#"><i class="fas fa-star"></i></a></li>
                    <li><a href="#"><i class="fas fa-star"></i></a></li>
                    <li><a href="#"><i class="fas fa-star-half-alt"></i></a></li>
                    <li><a href="#"><i class="far fa-star"></i></a></li>
                    <li class="review-total"> <a href="#"> ( 95 Reviews )</a></li>
                </ul>
            </div>
            <h3>` + product.productName + `</h3>
            <div class="product-price">
                <span>`+ product.productPrice +` 원</span>
                <!-- <del>$65.00</del> -->
            </div>
            <div class="modal-product-meta ltn__product-details-menu-1">
                <ul>
                    <li>
                        <strong>카테고리:</strong> 
                        <span>
                            <a href="#" id="product-category">` + product.categoryName + `</a>
                        </span>
                    </li>
                </ul>
            </div>
            <div class="ltn__product-details-menu-2">
                <ul>
                    <li>
                        <div class="cart-plus-minus">
                            <input type="number" value="1" name="qtybutton" class="cart-plus-minus-box" min=1 id="input-quantity">
                        </div>
                    </li>
                    <li>
                        <button class="theme-btn-1 btn btn-effect-1" title="내 장바구니에 상품을 추가합니다."  id="btn-add-to-cart">
                            <i class="fas fa-shopping-cart"></i>
                            <span>장바구니에 추가</span>
                        </button>
                        <button class="theme-btn-4 btn btn-effect-1" title="상품 구매 페이지로 이동합니다." id="btn-checkout">
                            <i class="fas fa-truck"></i>
                            <span>구매하기</span>
                        </button>
                    </li>
                </ul>
            </div>
            <div class="ltn__product-details-menu-3">
                <ul>
                    <li>
                        <a href="#" class="" title="Wishlist" data-bs-toggle="modal" data-bs-target="#liton_wishlist_modal">
                            <i class="far fa-heart"></i>
                            <span>찜 목록에 추가</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>`
}

function footerHTML(){
    return `<footer class="ltn__footer-area">
        <div class="footer-top-area  section-bg-2 plr--5">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xl-6 col-md-6 col-sm-6 col-12">
                        <div class="footer-widget footer-about-widget">
                            <div class="footer-logo">
                                <div class="site-logo">
                                    <a href="/" class="d-flex align-items-center">
                                        <h3 class="ltn__secondary-color mb-0">K</h3><h3 class="mb-0">-Market</h3>
                                   </a>
                                </div>
                            </div>
                            <p>코스타마켓은 통신판매중개자이며 통신판매의 당사자가 아닙니다. 따라서 코스타마켓은 상품·거래 정보 및 가격에 대하여 책임을 지지 않습니다.
                            본 사이트의 모든 정보, 콘텐츠, UI 등에 대한 무단 복제, 전송, 배포, 스크래핑 등의 행위는 관련 법령에 의하여 엄격히 금지됩니다. </p>
                            <div class="footer-address">
                                <ul>
                                    <li>
                                        <div class="footer-address-icon">
                                            <i class="icon-placeholder"></i>
                                        </div>
                                        <div class="footer-address-info">
                                            <p>서울특별시 금천구 가산디지털1로 70 호서대벤처타워 4층 401호</p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="footer-address-icon">
                                            <i class="icon-call"></i>
                                        </div>
                                        <div class="footer-address-info">
                                            <p><a href="tel:+10-6720-4257">+10-6720-4257</a></p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="footer-address-icon">
                                            <i class="icon-mail"></i>
                                        </div>
                                        <div class="footer-address-info">
                                            <p><a href="mailto:skyship36@gmail.com">skyship36@gmail.com</a></p>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div class="ltn__social-media mt-20">
                                <ul>
                                    <li><a href="https://github.com/Kosta-Juniors/kosta-market" title="Github"><i class="fab fa-github"></i> 프로젝트 보러가기</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-2 col-md-6 col-sm-6 col-12">
                        <div class="footer-widget footer-menu-widget clearfix">
                            <h4 class="footer-title">Company</h4>
                            <div class="footer-menu">
                                <ul>
                                    <li><a href="about.html">About</a></li>
                                    <li><a href="blog.html">Blog</a></li>
                                    <li><a href="shop.html">All Products</a></li>
                                    <li><a href="locations.html">Locations Map</a></li>
                                    <li><a href="faq.html">FAQ</a></li>
                                    <li><a href="contact.html">Contact us</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-2 col-md-6 col-sm-6 col-12">
                        <div class="footer-widget footer-menu-widget clearfix">
                            <h4 class="footer-title">Services</h4>
                            <div class="footer-menu">
                                <ul>
                                    <li><a href="order-tracking.html">Order tracking</a></li>
                                    <li><a href="/user/wishlist">Wish List</a></li>
                                    <li><a href="/user/signin">Login</a></li>
                                    <li><a href="/user/account">My account</a></li>
                                    <li><a href="about.html">Terms & Conditions</a></li>
                                    <li><a href="about.html">Promotional Offers</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-2 col-md-6 col-sm-6 col-12">
                        <div class="footer-widget footer-menu-widget clearfix">
                            <h4 class="footer-title">Customer Care</h4>
                            <div class="footer-menu">
                                <ul>
                                    <li><a href="/user/signin">Login</a></li>
                                    <li><a href="/user/account">My account</a></li>
                                    <li><a href="/user/wishlist">Wish List</a></li>
                                    <li><a href="order-tracking.html">Order tracking</a></li>
                                    <li><a href="faq.html">FAQ</a></li>
                                    <li><a href="contact.html">Contact us</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="ltn__copyright-area ltn__copyright-2 section-bg-7  plr--5">
            <div class="container-fluid ltn__border-top-2">
                <div class="row">
                    <div class="col-md-6 col-12">
                        <div class="ltn__copyright-design clearfix">
                        
                            <p>Copyright K-Market Inc. All Rights Reserved.<span class="current-year"></span></p>
                        </div>
                    </div>
                    <div class="col-md-6 col-12 align-self-center">
                        <div class="ltn__copyright-menu text-end">
                            <ul>
                                <li><a href="#">이용약관</a></li>
                                <li><a href="#">개인정보처리방침</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>`
}

function bannerComponent(banner){
    return `<a href="`+ banner.link +`">
                <img src="` + banner.imageSrc + `" alt="#">
            </a>`
}

function addressListComponent(userDeliveryList){
    let html = ""

    if(userDeliveryList == null || userDeliveryList.length == 0){
        return `
        <div class="col">
            <div>
                배송지가 등록되지 않았습니다. (등록하기)
            </div>
        </div>
        `
    }

    else{
        // addressId : 39,
        // isDefaultAddress : false
        for(delivery of userDeliveryList){
            html += `
            <div class="col-md-4">
                <div class="ltn__map-item" id=delivery-place-` + delivery.addressId + `>
                    <h3 class="ltn__location-name">` + delivery.title + `</h3>
                    <h4 class="ltn__location-single-info"><i class="fas fa-map-marked-alt"></i>` + delivery.deliveryPlace + `</h4>
                    <h4 class="ltn__location-single-info"><i class="fas fa-phone-volume"></i>` + delivery.recipient +`,<br>`+       delivery.contact+`</h4>
                    <div class="btn-wrapper">
                        <a href="appointment.html" class="btn btn-transparent btn-border btn-effect-4"><i class="fas fa-location-arrow"></i> 기본 배송지로 지정</a>
                        <a href="#" class="btn btn-transparent btn-border btn-effect-3"><i class="fas fa-globe"></i> 수정하기</a>
                    </div>
                </div>
            </div>`
        }
        return html
    }
}

function shopingCartTotalComponent(orderList){
    
    let header = `
    <div class="shoping-cart-total mt-50">
        <h4 class="title-2">구매 항목</h4>
        <table class="table">
            <tbody>`

    let footer = `
            </tbody>
        </table>
    </div>`

    let body = ``

    console.log(orderList)
    for(order of orderList){
        body += 
        `<tr>
            <td>`+ order.productName + `<strong> × `+order.quantity+`</strong></td>
            <td>`+ order.productPrice*order.quantity + ` 원</td>
        </tr>`
    }
    
    return header + body + footer
}

function shoppingCartItemsComponent(productList){
    let html = ""
    let index = 0

    for(product of productList){
        html += 
            `<tr id="cart-item-`+product.productId+`">
                <td class="cart-product-checkbox align-self-center">
                    <input type="checkbox" name="cart-product-select" value=`+product.productId+` checked>
                </td>ß
                <td class="cart-product-image">
                    <a href="/product/"`+ product.productId + `>
                        <img src="` + product.productImageFileName + `" alt="#">
                    </a>
                </td>
                <td class="cart-product-info">  
                    판매자:(주)수달친구들
                    <h4><a href="/product/"`+ product.productId + `>` + product.productName + `</a></h4>
                </td>
                <td class="cart-product-price" id="cart-product-price-`+product.productId+`">`
                    + product.productPrice +
                `</td>
                <td class="cart-product-quantity">
                    <div class="cart-plus-minus">
                        <input type="text" value="`+product.quantity+`" min="1" name="qtybutton" class="cart-plus-minus-box" id="cart-product-quantity-`+product.productId+`">
                    </div>
                </td>
                <td class="cart-product-subtotal">
                    <input type="number" value=` + product.productPrice * product.quantity + ` id="cart-product-subtotal-`+product.productId+`" readonly>
                </td>
                <td class="cart-product-remove" id="cart-product-remove-`+product.productId+`">X</td>
            </tr>`
    }

    return html
}

function orderListComponent(orderList){
    // if(userOrderList == null || userOrderList.length == 0){
    //     console.log("no order")
    // }
    // else{
    //     console.log('ok orderlist')
    // }
    let header = `
        <div class="ltn__myaccount-tab-content-inner">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th colspan=5>내 구매내역</th>
                        </tr>
                    </thead>
                    <tbody>`
    
    
    let body = ''
    for(order of orderList){
                    body += 
                        `<tr>
                            <td><strong>주문일자</strong></td>
                            <td colspan=4>`+order.orderDate+`</td>
                        </tr>
                        <tr>
                            <td rowspan=3><strong>배송지정보</strong></td>
                            <td><strong>주소</strong></td>
                            <td colspan=3>`+order.deliveryPlace+`</td>
                        </tr>
                        <tr>
                            <td><strong>수령인</strong></td>
                            <td colspan=4>`+order.deliveryName+`</td>
                        </tr>
                        <tr>
                            <td><strong>연락처</strong></td>
                            <td colspan=4>`+order.contact+`</td>
                        </tr>
                        <tr>
                            <td rowspan=`+(order.productList.length+1)+`><strong>상품정보</strong></td>
                        </tr>
                        `
        let productCnt = 0
        for(product of order.productList){
                    body +=
                        `<tr>
                            <td>`+product.productName+`</td>
                            <td>`+product.orderPrice+` 원 * `+product.orderQuantity+` 개</td>
                            <td><strong>주문상태</strong></td>
                            <td>`+product.orderState+`</td>
                        </tr>
                        `
            productCnt++
        }
                        body +=
                        `<tr class=mb-4>
                            <td colspan=2><strong>합계</strong></td>
                            <td colspan=2><strong>`+order.paymentPrice+` 원</strong></td>
                            <td><a href="/order/sheet/`+order.orderId+`">자세히 보기</a></td>
                        </tr>
                        <tr>
                            <td colspan=5></td>
                        <tr>`
    }
    let footer = `
                    </tbody>
                </table>
            </div>
        </div>`

    return header + body + footer
}
function relatedProductSliderComponent(productList){
    
    let html = ``

    for(product of productList){
        html += `<!-- ltn__product-item -->
        <div class="col-lg-12">
            <div class="ltn__product-item ltn__product-item-3 text-center">
                <div class="product-img">
                    <a href="/product/`+ product.productId +`"><img src="http://127.0.0.1/img/product/7.png" alt="#"></a>
                    <div class="product-badge">
                        <ul>
                            <li class="sale-badge">New</li>
                        </ul>
                    </div>
                    <div class="product-hover-action">
                        <ul>
                            <li>
                                <a href="#" title="Quick View" data-bs-toggle="modal" data-bs-target="#quick_view_modal">
                                    <i class="far fa-eye"></i>
                                </a>
                            </li>
                            <li>
                                <a href="#" title="Add to Cart" data-bs-toggle="modal" data-bs-target="#add_to_cart_modal">
                                    <i class="fas fa-shopping-cart"></i>
                                </a>
                            </li>
                            <li>
                                <a href="#" title="Wishlist" data-bs-toggle="modal" data-bs-target="#liton_wishlist_modal">
                                    <i class="far fa-heart"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="product-info">
                    ` + commentStarComponent(5) + `
                    <h2 class="product-title"><a href="/product/`+product.productId+`">`+product.productName+`</a></h2>
                    <div class="product-price">
                        <span>` + product.productPrice + ` 원</span>
                        <del>$162.00</del>
                    </div>
                </div>
            </div>
        </div>`
    }

    return html
}

function topRatedProductListComponent(topRatedProductList){

    let topRatedProductHTML = ""
                
    for(let topRatedProduct of topRatedProductList){
        topRatedProductHTML += `
            <li>
                <div class="top-rated-product-item clearfix">
                    <div class="top-rated-product-img">
                        <a href="` + topRatedProduct.productId + `"><img src="` + topRatedProduct.productImageFileName + `" alt="#"></a>
                    </div>
                    <div class="top-rated-product-info">
                        <div class="product-ratting">
                            <ul>`

        for(let index=0; index<5; index++){
            if(index < topRatedProduct.score)
                topRatedProductHTML += `<li><a href="#"><i class="fas fa-star"></i></a></li>`
            else
                topRatedProductHTML += `<li><a href="#"><i class="far fa-star"></i></a></li>`
        }

        topRatedProductHTML += `
                            </ul>
                        </div>
                        <h6><a href="/product/`+ topRatedProduct.productId +`">` + topRatedProduct.productName+`</a></h6>
                        <div class="product-price">
                            <span>` + topRatedProduct.productPrice + `원</span>
                            <del>$65.00</del>
                        </div>
                    </div>
                </div>
            </li>`
    }
    return topRatedProductHTML
}

function commentStarComponent(userScore){
    let starHeader = `<div class="product-ratting"><ul>`
    let starItem = ``
    let starFooter = `</ul></div>`
    
    for(let starCnt=0; starCnt<5; starCnt++){
        if(starCnt <= userScore)
            starItem += `<li><a href="#"><i class="fas fa-star"></i></a></li>`
        else
            starItem += `<li><a href="#"><i class="far fa-star"></i></a></li>`   
    }

    return starHeader + starItem + starFooter
}




// function renderIndex(){}
// function renderProductList()
// function renderOrderList()

// const HTML_COMPONENT = {
//     user : {},
//     order : {},
//     product : {
//         gridItem : {},
//         listItem : {},
//         review : {},
//         score : {}
//     }
// }