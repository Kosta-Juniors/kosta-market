async function getBanner(){
    return Promise.resolve({link : "", imageSrc : "http://127.0.0.1/img/banner/2.jpg"})
    return new Promise((resolve, reject) => {
        $.ajax({
            type : "get",
            url : SERVER_URL + "/api/product/???",
            success: function(response){
                resolve()
            },
            error : function(re){
                reject()
            }
        })
    })
}
async function getTopRatedProductList(){
    return Promise.resolve(
        [
            {
                productId : 1,
                productName : "테스트1",
                score : 1,
                productPrice : 10000,
                productDescription : "가짜 설명",
                productImageFileName : "http://127.0.0.1/img/product/1.png"
            },
            {
                productId : 2,
                productName : "테스트2",
                productPrice : 2000,
                score : 3,
                productDescription : "가짜 설명",
                productImageFileName : "http://127.0.0.1/img/product/1.png"
            },
            {
                productId : 3,
                productName : "테스트3",
                productPrice : 40000,
                score : 1,
                productDescription : "가짜 설명",
                productImageFileName : "http://127.0.0.1/img/product/1.png"
            }
        ]
    )
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "get",
            url: SERVER_URL + "/api/product?sortby=top",
            success:function(data){
                const jsonData = JSON.parse(data).data
                resolve(jsonData)
            },
            error:function(data){
                reject(null)
            }
        })
    })
}
async function getCategoryList(){
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : SERVER_URL + "/api/category",
            success : function(response){
                resolve(JSON.parse(response).data);
            }
        })
    })
}
async function getTotalReviewScore(productId){
    return Promise.resolve({
        score : 5,
        reviewCount : 50120
    })
    return new Promise((resolve, reject) => {
        $.ajax({
            type:'get',
            url:SERVER_URL + "/api/product/"+productId+"/comment????",
            success:function(response){
                resolve(JSON.parse(response).data);
            }
        })
    })
}
async function getReviewList(productId, page, size){
    return Promise.resolve([
        {
            commentId : 23,
            title : "이 상품은 매우 좋습니다!",
            content : "나는 사용중이다 10년간 이 상품만을 하지만 그것은 나를 만족시키기에 충분히 매력적인 상품인것, 판매자에게 감사",
            score : 3,
            author : "Jason Mac",
            authorImageUrl : "http://127.0.0.1/img/testimonial/1.jpg",
            createdAt : "2022-08-01 12:18:00"
        },
        {
            commentId : 33,
            title : "이건 좋지 않아 -_-",
            content : "기존의 상품평과 다른. 거짓말이 많다 문제! 관리를 해야할까요",
            score : 4,
            author : "Bella chio",
            authorImageUrl : "http://127.0.0.1/img/testimonial/1.jpg",
            createdAt : "2022-08-01 12:18:00"
        },
        {
            commentId : 55,
            title : "이 상품은 매우 좋습니다!",
            content : "나는 사용중이다 10년간 이 상품만을 하지만 그것은 나를 만족시키기에 충분히 매력적인 상품인것, 판매자에게 감사",
            score : 2,
            author : "Jason Mac",
            authorImageUrl : "http://127.0.0.1/img/testimonial/1.jpg",
            createdAt : "2022-08-01 12:18:00"
        },
        {
            commentId : 6623,
            title : "이 상품은 매우 좋습니다!",
            content : "나는 사용중이다 10년간 이 상품만을 하지만 그것은 나를 만족시키기에 충분히 매력적인 상품인것, 판매자에게 감사",
            score : 3,
            author : "Jason Mac",
            authorImageUrl : "http://127.0.0.1/img/testimonial/1.jpg",
            createdAt : "2022-08-01 12:18:00"
        },
        {
            commentId : 253,
            title : "이 상품은 매우 좋습니다!",
            content : "나는 사용중이다 10년간 이 상품만을 하지만 그것은 나를 만족시키기에 충분히 매력적인 상품인것, 판매자에게 감사",
            score : 5,
            author : "Jason Mac",
            authorImageUrl : "http://127.0.0.1/img/testimonial/1.jpg",
            createdAt : "2022-08-01 12:18:00"
        },
        {
            commentId : 123,
            title : "이 상품은 매우 좋습니다!",
            content : "나는 사용중이다 10년간 이 상품만을 하지만 그것은 나를 만족시키기에 충분히 매력적인 상품인것, 판매자에게 감사",
            score : 1,
            author : "Jason Mac",
            authorImageUrl : "http://127.0.0.1/img/testimonial/1.jpg",
            createdAt : "2022-08-01 12:18:00"
        },
        {
            commentId : 26663,
            title : "이 상품은 매우 좋습니다!",
            content : "나는 사용중이다 10년간 이 상품만을 하지만 그것은 나를 만족시키기에 충분히 매력적인 상품인것, 판매자에게 감사",
            score : 4,
            author : "Jason Mac",
            authorImageUrl : "http://127.0.0.1/img/testimonial/1.jpg",
            createdAt : "2022-08-01 12:18:00"
        }
    ])
}
async function getRelatedProductList(){
    return Promise.resolve([
        {
            productId : 31,
            productName : "be the 0 oot bang",
            productPrice : 40000,
            score : 1,
            productDescription : " 가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.",
            productImageFileName : "http://127.0.0.1/img/product/1.png"
        },
        {
            productId : 31,
            productName : "be the 0 oot bang",
            productPrice : 40000,
            score : 1,
            productDescription : " 가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.",
            productImageFileName : "http://127.0.0.1/img/product/1.png"
        },

        {
            productId : 44,
            productName : "iwanna",
            productPrice : 40000,
            score : 1,
            productDescription : " 가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.",
            productImageFileName : "http://127.0.0.1/img/product/1.png"
        },
        {
            productId : 33,
            productName : "33333333",
            productPrice : 40000,
            score : 1,
            productDescription : " 가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.",
            productImageFileName : "http://127.0.0.1/img/product/1.png"
        },
        {
            productId : 93,
            productName : "aaaaaaaa",
            productPrice : 40000,
            score : 1,
            productDescription : " 가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.",
            productImageFileName : "http://127.0.0.1/img/product/1.png"
        }
    ])
}
async function getProduct(productId){
    return Promise.resolve(
        {
            productId : productId,
            productName : "상품명"+productId,
            productPrice : 1000*productId,
            categoryName : "카테고리 1",
            score : productId%5,
            productDescription : " 가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.가짜 설명입니다. 가짜설명입니다.",
            productImageFileName : "http://127.0.0.1/img/product/1.png"
        }
    )

    return new Promise((resolve, reject) => {
        $.ajxa({
            type : "GET",
            url : SERVER_URL + "/api/product/" + productId,
            success : function(response){
                resolve(JSON.parse(response).data);
            },
            error : function(error){
                reject(error);
            }
        })
    })
}
async function searchProduct(query){
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : SERVER_URL + "/api/product?search=" + query,
            success :function(response){
                resolve(JSON.parse(response.data));
            }
        })
    })
}

function addProduct(){
    const name = $("#productName").val();
    const price = $("#productPrice").val();
    const description = $("#productDescription").val();
    const quantity = $("#productQuantity").val();

    $.ajax({
        type : 'POST',
        url : SERVER_URL + "/api/product",
        data : JSON.stringify({
            name : name,
            price : price,
            description : description,
            quantity : quantity
        }),
        contentType : "application/json; charset=utf-8",
        error : function(){
            alert("fail");
        },
        success : function(){
            alert("success")
        }
    })
}

async function getProductListByCategoryIdAndPageAndSizeAndSearch(categoryId, page, size, search){
    let dummyData = [
        {"categoryId" : categoryId},
        {"page" : page},
        {"size" : size},
        {"search" : search}
    ]
    
    let searchQuery = "?"

    dummyData.filter(item => Object.values(item)[0] != null)
        .forEach(item => { searchQuery += Object.keys(item)[0]+"="+Object.values(item)[0]+"&" })


    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : SERVER_URL + "/api/product"+searchQuery,
            success : function(response){
                resolve(JSON.parse(response).data)
            }
        })
    });
}

function getSellerProductListByCategoryIdAndPageAndSize(sellerId, categoryId, page, size){
    $.ajax({
        type : 'GET',
        url : SERVER_URL + "/api/product?" + "sellerId="+sellerId+ "&categoryId="+categoryId+"&page="+page+"&size="+size,
        error : function(){
            alert("fail");
        },
        success : function(){
            alert("success")
        }
    })
}
function editProduct(){}
function deleteProduct(){}
function getCart(){
    $.ajax({
        type : 'GET',
        url : SERVER_URL + "/api/product/cart",
        error : function(){
            alert("fail");
        },
        success : function(){
            alert("success")
        }
    })
}
async function addCart(productId, quantity){
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'POST',
            url : SERVER_URL + "/api/product/cart",
            data : JSON.stringify({
                productId : productId,
                quantity : quantity
            }),
            error : function(){
                reject()
            },
            success : function(){
                resolve()
            }
        })
    })
}

function patchCart(productId){
    const currentQuantity = $("#productQuantity").val()

    $.ajax({
        type : 'PATCH',
        url : SERVER_URL + "/api/product/cart/"+productId,
        data : JSON.stringify({
            quantity : currentQuantity
        }),
        error : function(){
            alert("fail");
        },
        success : function(){
            alert("success")
        }
    })
}

function deleteCart(productId){
    $.ajax({
        type : 'DELETE',
        url : SERVER_URL + "/api/product/cart/"+productId,
        error : function(){
            alert("fail");
        },
        success : function(){
            alert("success")
        }
    })
}
function getWishlist(){

}
function addWishlist(){

}
function deleteWishlist(){

}