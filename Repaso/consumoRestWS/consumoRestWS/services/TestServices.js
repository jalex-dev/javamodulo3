

export const getAllPostsService = () => {
    fetch('https://jsonplaceholder.typicode.com/posts')
        .then((response) => { return response.json() })
        .then((json) => { console.log(json) });
}

const config = {
    method:'Post',
    body: JSON.stringify({
        title:'mensaje',
        body:' suerte en su evalucion',
        userId: 1
    }),
    headers: {
        'Content-type':'application/json'
    }
}

export const createPostService = (post,fnExito) => {
    const config = {
        method:'Post',
        body: JSON.stringify({
            title:post.title,
            body:post.body,
            userId: 1
        }),
        headers: {
            'Content-type':'application/json'
        }
    }
    fetch('https://jsonplaceholder.typicode.com/posts', config)
    .then((response) => { return response.json() })
    .then((json) => { console.log(json);fnExito(); });

}
// ahorra quiero lo mismo para update
export const updatePostService = () =>{
    const config = {
        method:'PUT',

        body: JSON.stringify({
            title:'mensaje final',
            body:' hasta la vista baby',
            userId: 1,
            id:1
        }),
        headers: {
            'Content-type':'application/json'
        }
    }
    fetch('https://jsonplaceholder.typicode.com/posts/1', config)
    .then((response) => { return response.json() })
    .then((json) => { console.log(json) });
}

export const getByUserService = ()=>{
    fetch('https://jsonplaceholder.typicode.com/posts?userId=1')
        .then((response) => { return response.json() })
        .then((json) => { console.log(json) });
}

export const getAllProductsService = ()=>{
    fetch('https://fakestoreapi.com/products')
            .then(res=>res.json())
            .then(json=>console.log(json))
}

export const addNewProductService=()=>{
    const config= {
        method:"POST",
        body:JSON.stringify(
            {
                title: 'Coca cola',
                price: 13.5,
                description: 'lorem ipsum set',
                image: 'https://i.pravatar.cc',
                category: 'Bebida'
            }
        )
    }
    fetch('https://fakestoreapi.com/products',config)
            .then(res=>res.json())
            .then(json=>console.log(json))
}

export const updateProdcutoService=()=>{
    fetch('https://fakestoreapi.com/products/7',{
            method:"PUT",
            body:JSON.stringify(
                {
                    title: 'test product',
                    price: 13.5,
                    description: 'lorem ipsum set',
                    image: 'https://i.pravatar.cc',
                    category: 'electronic'
                }
            )
        })
            .then(res=>res.json())
            .then(json=>console.log(json))
}

//servicos tippos ducumento

export const getDocumentTypeService = () => {
    fetch('http://192.168.3.38:8080/inventario2-1.0.0/rest/tipodocumento/recuperar')
        .then((response) => { return response.json() })
        .then((json) => { console.log(json) });
}

export const insertDocumentTypeService = (body,fnExito) => {
    fetch('http://192.168.3.38:8080/inventario2-1.0.0/rest/tipodocumento/insertar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
    .then((response) => {  
        return response.json();
    })
    .then((json) => { 
        console.log(json); fnExito();
    })
    ;
}