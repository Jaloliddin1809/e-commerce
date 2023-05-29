let left = document.querySelector(".left")
let right = document.querySelector(".right")
let render = document.querySelector('.render')

function checkScreenChanges() {
    const WindowWidth = parseInt(window.innerWidth) - 287
    right.style.width = `${WindowWidth}px`
    window.requestAnimationFrame(checkScreenChanges);
}

window.requestAnimationFrame(checkScreenChanges);

function checkScreenChanges2() {
    const WindowWidth = parseInt(window.innerHeight) - 89
    render.style.height = `${WindowWidth}px`
    window.requestAnimationFrame(checkScreenChanges2);
}

window.requestAnimationFrame(checkScreenChanges2);




let category = document.querySelector('#category');
let product = document.querySelector('#product');
let orders = document.querySelector('#orders');
let employes = document.querySelector('#employes');

function openCategorys() {
    category.style.display = "block"
    product.style.display = "none"
    orders.style.display = "none"
    employes.style.display = "none"
}

function openProducts() {
    category.style.display = "none"
    product.style.display = "block"
    orders.style.display = "none"
    employes.style.display = "none"
}

function openOrders() {
    category.style.display = "none"
    product.style.display = "none"
    orders.style.display = "block"
    employes.style.display = "none"
}

function openEmployes() {
    category.style.display = "none"
    product.style.display = "none"
    orders.style.display = "none"
    employes.style.display = "block"
}