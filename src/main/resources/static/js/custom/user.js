
//$("#btn-delete-delivery").click(addDelivery)

$("#btn-modify-user").click()
$("#btn-delete-user").click()

const SERVER_URL = "http://127.0.0.1";

function register(username, password, name){
    
    $.ajax({
        type : 'post',
        url : SERVER_URL + "/api/user/signup",
        data : JSON.stringify({
            name : name,
            username : username,
            password : password
        }),
        contentType : "application/json; charset=utf-8",
        error : function(){
            alert("fail");
            window.location.pathname = "/user/signin"
        },
        success : function(){
            alert("게정이 생성되었습니다.")
            window.location.pathname = "/user/signin"
        }
    });
}

async function login(username, password){
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'post',
            url : SERVER_URL + "/api/user/signin",
            data : JSON.stringify({
                username : username,
                password : password
            }),
            contentType : "application/json; charset=utf-8",
            error : function(){
                reject()
            },
            success : function(){
                resolve()
            }
        })
    })
}

async function logout(){
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'post',
            url : SERVER_URL + "/api/user/signout",
            error : function(){reject()},
            success : function(){resolve()}
        })
    })
}

async function getUser(){
    
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'GET',
            url : SERVER_URL + "/api/user",
            success: function(response){
                const jsonData = JSON.parse(response).data
                resolve(jsonData)
            },
            error: function(jqXHR, b, c){
                reject(null)
            }
        })
    })
}

async function retireUser(password){
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'DELETE',
            url : SERVER_URL + "/api/user",
            data : JSON.stringify({
                password : password
            }),
            success: function(){ resolve() },
            error: function(){ reject() }
        })
    })
}

async function modifyUser(nickname, password){
    return new Promise((resolve, reject) => { 
        $.ajax({
            type: 'PUT',
            url: SERVER_URL + "/api/user",
            data: JSON.stringify({
                nickname : nickname,
                password : password
            }),
            contentType: "application/json; charset=utf-8",
            success:function(){ alert("ok"); resolve() },
            error:function(){ reject() }
        })
    })
}

async function getDelivery(){
    return Promise.resolve([
        {
            addressId : 32,
            title : "우리집",
            recipient : "정완규",
            contact : "010-6720-4275",
            deliveryPlace : "관악구 관천로 60 신림 신원시장로",
            isDefaultAddress : true
        },
        {
            addressId : 31,
            title : "회사",
            recipient : "코스타 ",
            contact : "02-9910-4275",
            deliveryPlace : "금천쿰청역 1번출구 노숙자 10호상자",
            isDefaultAddress : false
        },
        {
            addressId : 39,
            title : "회사",
            recipient : "코스타 ",
            contact : "02-9910-4275",
            deliveryPlace : "금천쿰청역 1번출구 노숙자 10호상자",
            isDefaultAddress : false
        }
    ])
    return new Promise((resolve, reject) => {
        $.ajax({
            type : 'get',
            url : SERVER_URL + "/api/user/address",
            success : function(response){
                const jsonData = JSON.parse(response).data;
                resolve(jsonData)
            },
            error : function(){
                reject(null)
            }
        })
    })
}

async function getMyCart(){
    return Promise.resolve([
        {
            productId : 12,
            quantity : 10
        },
        {
            productId : 3,
            quantity : 1
        },
        {
            productId : 6,
            quantity : 3
        },{
            productId : 9,
            quantity : 2
        }
    ])
    return new Promise((resolve,reject) => {
        $.ajax({
            type : "get",
            url : SERVER_URL + "/api/cart",
            success : function(response){
                resolve(JSON.parse(response).data)
            },
            fail : function(error){
                reject(error);
            }
        })
    })
}

function addDelivery(title, name, phone, address, detailAddress){
    $.ajax({
        type : 'post',
        url : SERVER_URL + "/api/user/delivery",
        data : JSON.stringify({
            title : title,
            name : name,
            phone : phone,
            address : address,
            detailAddress : detailAddress
        }),
        contentType : "application/json; charset=utf-8",
        error : function(){
            alert("배송지 추가 실패");
        },
        success : function(){
            alert("success")
        }
    });
}

function deleteDelivery(){
    $.ajax({
        type : 'delete',
        url : SERVER_URL + "/api/user/delivery",
        data : JSON.stringify({
            deliveryId : $("#deliveryId").val(),
        }),
        contentType : "application/json; charset=utf-8",
        error : function(){
            alert("fail");
        },
        success : function(){
            alert("success")
        }
    });
}

function addSeller(){

}
function deleteSeller(){

}