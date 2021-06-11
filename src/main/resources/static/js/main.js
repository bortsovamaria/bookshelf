
function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var bookApi = Vue.resource('/books{/id}');

Vue.component('book-form', {
    props: ["books", "bookAttr"],
    data: function () {
        return {
            book: '',
            id: ''
        }
    },
    watch: {
        bookAttr: function (newVal, oldVal) {
            this.book = newVal.book;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="Write something" v-model="book"/>' +
            '<input type="button" value="Save" @click="save" />' + <!-- v-on:click -->
        '</div>',
    methods: { <!-- описание метода save -->
        save: function () {
            var book = { book: this.book };

            if (this.id) {
                bookApi.update({id: this.id}, book).then(result =>
                result.json().then(data => {
                    var index = getIndex(this.books, data.id);
                    this.books.splice(index, 1, data);
                    this.book = '';
                    this.id = '';
                }))
            } else {
                bookApi.save({}, book).then(result =>
                    result.json().then(data => {
                        this.books.push(data);
                        this.book = ''
                    })
                )
            }
        }
    }
});

Vue.component('book-row', {
    props: ['book', 'editBook', 'books'],
    template: '<div>' +
        '<i>({{ book.id }})</i> {{ book.book }}' +
        '<span style="position: absolute; right: 0">' +
            '<input type="button" value="Edit" @click="edit"/>' +
            '<input type="button" value="X" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editBook(this.book);
        },
        del: function () {
            bookApi.remove({id: this.book.id}).then(result => {
                if (result.ok) {
                    this.books.splice(this.books.indexOf(this.book), 1)
                }
            })
        }
    }
});

Vue.component('books-list', {
    props: ['books'],
    data: function() {
        return {
            book: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
            '<book-form :books="books" :bookAttr="book"/>' +
            '<book-row v-for="book in books" :key="book.id" :book="book" ' +
                ':editBook="editBook" :books="books" />' +
        '</div>',
    created: function () {
        bookApi.get().then(result =>
            result.json().then(data =>
                data.forEach(book => this.books.push(book))
            )
        )
    },
    methods: {
        editBook: function (book) {
            this.book = book;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<books-list :books="books" />',
    data: {
        books: []
    }
});