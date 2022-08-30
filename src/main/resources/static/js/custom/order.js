async function addOrder(orderListQuery, paymentMethod, addressId){
    //return Promise.resolve()

    //orderObjList.
    return new Promise((resolve, reject) => {
        $.ajax({
            type : "post",
            url : SERVER_URL + "/api/order" + orderListQuery,
            body : JSON.stringify({
                paymentMethod : paymentMethod,
                addressId : addressId
            }),
            success : function(res){
                // const jsonData = JSON.parse(res).data 
                // resolve(jsonData)
                resolve()
            },
            fail : function(err){
                reject(err);
            }
        })
    })
}

async function getOrderList(){
    return Promise.resolve(
        [
            {
                "orderId" : 12,
                "orderDate" : "2022-01-01 11:11:11",
                "productList" : [
                    {
                        "productId" : 10,
                        "productName" : "주문한 상품-1",
                        "orderQuantity" : 1,
                        "orderState" : '0',
                        "orderPrice" : 10000,
                    }
                ],
                "paymentMethod" : "00",
                "paymentPrice" : 50000,
                "deliveryName" : "완규",
                "deliveryPlace" : "서울특별시 금천구 금천로 55, 401호",
                "contact" : "010-6720-4257" 
            },
            {
                "orderId" : 52,
                "orderDate" : "2022-01-01 11:11:11",
                "productList" : [
                    {
                        "productId" : 10,
                        "productName" : "주문한 상품-1",
                        "orderQuantity" : 1,
                        "orderState" : '0',
                        "orderPrice" : 10000,
                    },{
                        "productId" : 102,
                        "productName" : "주문한 상품-1",
                        "orderQuantity" : 1,
                        "orderState" : '0',
                        "orderPrice" : 10000,
                    },{
                        "productId" : 10,
                        "productName" : "주문한 상품-1",
                        "orderQuantity" : 1,
                        "orderState" : '0',
                        "orderPrice" : 10000,
                    },{
                        "productId" : 10,
                        "productName" : "주문한 상품-1",
                        "orderQuantity" : 1,
                        "orderState" : '0',
                        "orderPrice" : 10000,
                    }
                ],
                "paymentMethod" : "00",
                "paymentPrice" : 50000,
                "deliveryName" : "완규",
                "deliveryPlace" : "서울특별시 금천구 금천로 55, 401호",
                "contact" : "010-6720-4257" 
            },
            {
                "orderId" : 62,
                "orderDate" : "2022-01-01 11:11:11",
                "productList" : [
                    {
                        "productId" : 10,
                        "productName" : "주문한 상품-1",
                        "orderQuantity" : 1,
                        "orderState" : '0',
                        "orderPrice" : 10000,
                    }
                ],
                "paymentMethod" : "00",
                "paymentPrice" : 20000,
                "deliveryName" : "완규",
                "deliveryPlace" : "서울특별시 금천구 금천로 55, 401호",
                "contact" : "010-6720-4257" 
            },
        ]
    )
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "get",
            url: "/api/order/ordersheet",
            success: function(response){
                const jsonData = JSON.parse(response).data
                resolve(jsonData)
            },
            error: function(response){ 
                reject(null)
            }
        })
    })
}
function getOrderListBySellerId(){}
function getOrder(){}
function exchangeProductRequest(){}
function refundRequest(){}
function confirmOrder(){}
function cancelOrder(){}

