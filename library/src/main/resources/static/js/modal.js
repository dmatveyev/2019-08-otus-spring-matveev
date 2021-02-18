$('#exampleModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Кнопка, что спровоцировало модальное окно

    var author = button.data('whatever')

    alert(author)
    // Извлечение информации из данных-* атрибутов

    // Если необходимо, вы могли бы начать здесь AJAX-запрос (и выполните обновление в обратного вызова).

    // Обновление модальное окно Контента. Мы будем использовать jQuery здесь, но вместо него можно использовать привязки данных библиотеки или других методов.

    var modal = $(this)
    modal.find('.modal-title').text('New message to ' + author)
    modal.find('.modal-body ').val(author)
})