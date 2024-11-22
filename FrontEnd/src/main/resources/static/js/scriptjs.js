$(document).ready(function() {
    $('.carousel2 .carousel-item2').each(function () {
        var minPerSlide = 4;
        var next = $(this).next();
        if (!next.length) {
            next = $(this).siblings(':first');
        }
        next.children(':first-child').clone().appendTo($(this));

        for (var i = 0; i < minPerSlide; i++) {
            next = next.next();
            if (!next.length) {
                next = $(this).siblings(':first');
            }

            next.children(':first-child').clone().appendTo($(this));
        }
    });
});

//funcion para añadir a favortios


document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.btn-outline-warning');
    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const id = button.getAttribute('data-id');
            const title = button.getAttribute('data-title');
            const price = button.getAttribute('data-price');
            const thumbnail = button.getAttribute('data-thumbnail');
            const description = button.getAttribute('data-description') || 'Descripción no disponible';
            let favoritos = JSON.parse(localStorage.getItem('favoritos')) || [];
            const productoExistente = favoritos.find(item => item.id === id);
            if (productoExistente) {
                alert('Ya añadiste este producto a favoritos.');
            } else {
                favoritos.push({id, title, price, thumbnail, description});
                alert('Añadido a favoritos.');
                localStorage.setItem('favoritos', JSON.stringify(favoritos));
            }
        });
    });
});

$(document).ready(function () {

    // Función para renderizar productos
    function renderProductos(productos) {
        $('#productos-list').empty(); // Limpiar el contenedor
        productos.forEach(function (producto) {
            let productCard = `
                <div class="col mb-5" data-marca="${producto.marca}">
                    <div class="card h-100">
                        <img class="card-img-top" src="http://localhost:8080/api/productos/imagen/${producto.idProducto}" alt="${producto.nombre}" />
                        <div class="card-body p-4">
                            <div class="text-center">
                                <h5 class="fw-bolder">${producto.nombre}</h5>
                                <p>${producto.detalle}</p>
                                <p class="h4">S/. ${producto.precio}</p>
                            </div>
                        </div>
                        <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                            <div class="text-center">
                                <button type="button" class="btn btn-outline-dark mt-auto add-to-cart-btn" data-id="${producto.idProducto}">Agregar</button>
                                <a class="btn btn-outline-secondary mt-auto" href="/productos/detalles/${producto.idProducto}">Detalles</a>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            $('#productos-list').append(productCard);
        });
    }


    $.ajax({
        url: 'http://localhost:8080/api/productos', // URL completa del backend
        type: 'GET',
        success: function (response) {
            console.log('Productos cargados:', response);
            renderProductos(response); // Renderizar productos en la página
        },
        error: function (err) {
            console.error('Error al cargar todos los productos:', err);
        }
    });


    $('.filter-btn').click(function () {
        let marcaSeleccionada = $(this).data('marca');

        $.ajax({
            url: '/productos/por-marca',
            type: 'GET',
            data: { marca: marcaSeleccionada },
            success: function (response) {
                console.log('Productos filtrados:', response);
                renderProductos(response);
            },
            error: function (err) {
                console.error('Error al obtener productos por marca:', err);
            }
        });
    });
});
$(document).ready(function () {
    let carrito = [];


    function renderCarrito() {
        const $cartItems = $('#cart-items');
        $cartItems.empty(); 
        carrito.forEach((producto, index) => {
            $cartItems.append(`
                <li class="d-flex justify-content-between align-items-center mb-2">
                    <div>
                        <img src="${producto.imagen}" alt="${producto.nombre}" style="width: 50px; height: 50px; object-fit: cover;" />
                        <span>${producto.nombre}</span>
                    </div>
                    <div>
                        <span>S/. ${producto.precio}</span>
                        <button class="btn btn-sm btn-danger remove-from-cart" data-index="${index}">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </div>
                </li>
            `);
        });

        if (carrito.length === 0) {
            $cartItems.append('<li class="text-center text-muted">El carrito está vacío</li>');
        }
    }


    $('#cart').click(function () {
        $('#cart-popup').toggle();
    });


    $(document).on('click', '.add-to-cart-btn', function () {
        const producto = {
            id: $(this).data('id'),
            nombre: $(this).closest('.card').find('h5').text(),
            precio: parseFloat($(this).closest('.card').find('.h4').text().replace('S/. ', '')),
            imagen: $(this).closest('.card').find('img').attr('src')
        };

        carrito.push(producto); 
        localStorage.setItem('carrito', JSON.stringify(carrito)); 
        renderCarrito(); 
    });

    
    $(document).on('click', '.remove-from-cart', function () {
        const index = $(this).data('index');
        carrito.splice(index, 1);
        localStorage.setItem('carrito', JSON.stringify(carrito));
        renderCarrito(); 
    });

    
    $('#procesar-compra').click(function () {
        if (carrito.length > 0) {
           
            localStorage.setItem('carrito', JSON.stringify(carrito));
            window.location.href = '/checkout';
        } else {
            alert('El carrito está vacío.');
        }
    });

    
    carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    renderCarrito();
});
    // $(document).ready(function () {
    //     $('#loginForm').submit(function (event) {
    //         event.preventDefault();
    //
    //         const email = $('#correo').val();
    //         const password = $('#password').val();
    //
    //         fetch('http://localhost:8080/api/usuarios/login', {
    //             method: 'POST',
    //             headers: {
    //                 'Content-Type': 'application/json'
    //             },
    //             body: JSON.stringify({ correo: email, password: password })
    //         })
    //             .then(response => {
    //                 if (response.ok) {
    //                     localStorage.setItem('userEmail', email);
    //                     return response.json();
    //                 } else {
    //                     throw new Error('Login fallido');
    //                 }
    //             })
    //             .then(data => {
    //                 if (data.token) {
    //                     localStorage.setItem('authToken', data.token); // Store the token
    //                 } else {
    //                     console.warn("No authToken received in the response");
    //                 }
    //                 window.location.href = '/';
    //             })
    //             .catch(error => {
    //                 console.error('Error durante el login:', error);
    //                 $('#error').text('Login fallido. Por favor, verifica tus credenciales.');
    //             });
    //     });
    //
    //     async function getUserDetails() {
    //         const email = localStorage.getItem('userEmail');
    //         const authToken = localStorage.getItem('authToken');
    //         const userNameElement = $('#userName');
    //         if (email && authToken) {
    //             try {
    //                 const response = await fetch(`http://localhost:8080/api/usuarios/email?email=${email}`, {
    //                     headers: {
    //                         'Authorization': `Bearer ${authToken}`
    //                     }
    //                 });
    //
    //                 if (response.ok) {
    //                     const user = await response.json();
    //                     userNameElement.text(user.nombre);
    //                     $('#userMenu').hide();
    //                 } else {
    //                     console.error('Error al obtener los detalles del usuario');
    //                     setLoginView();
    //                 }
    //             } catch (error) {
    //                 console.error('Error fetching user details:', error);
    //                 setLoginView();
    //             }
    //         } else {
    //             setLoginView();
    //         }
    //     }
    //
    //     function setLoginView() {
    //         $('#userName').text('Login');
    //         $('#userMenu').hide();
    //         $('#userButton').on('click', function () {
    //             window.location.href = '/login';
    //         });
    //     }
    //
    //     getUserDetails();
    //
    //
    //     $('#userMenu').find('[href="/logout"]').click(function (event) {
    //         event.preventDefault();
    //         localStorage.removeItem('userEmail');
    //         localStorage.removeItem('authToken');
    //         setLoginView();
    //         window.location.href = '/login';
    //     });
    //
    //     $('#userButton').click(function (event) {
    //         event.stopPropagation();
    //         $('#userMenu').toggle();
    //     });
    //
    //     $(document).click(function (event) {
    //         if (!$(event.target).closest('#userButton').length && !$(event.target).closest('#userMenu').length) {
    //             $('#userMenu').hide();
    //         }
    //     });
    //
    //
    //
    // });

// function checkAuthenticated() {
//     const authToken = localStorage.getItem('authToken');
//     const userEmail = localStorage.getItem('userEmail');
//
//     if (!authToken || !userEmail) {
//         alert('Please log in to proceed to checkout.');
//         window.location.href = '/login';
//         return false;
//     }
//     return true;
// }
