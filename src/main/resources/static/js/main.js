
var bookApi = Vue.resource('/books{/id}');

Vue.component('book-row', {
    props: ['book'],
    template: '<div><i>({{ book.id }})</i> {{ book.book }}</div>'
});

Vue.component('books-list', {
    props: ['books'],
    template:
        '<div>' +
            '<book-row v-for="book in books" :key="book.id" :book="book" />' +
        '</div>',
    created: function () {
        bookApi.get().then(result =>
            result.json().then(data =>
                data.forEach(book => this.books.push(book))
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    template: '<books-list :books="books" />',
    data: {
        books: []
    }
});