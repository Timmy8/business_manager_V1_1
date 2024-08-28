document.addEventListener('DOMContentLoaded', function () {
    const dateSelect = document.getElementById('visitDate');
    const timeSelect = document.getElementById('visitTime');

    // Функция для создания списка дат на ближайший месяц
    function populateDates() {
        const today = new Date();
        for (let i = 0; i < 30; i++) {
            const date = new Date();
            date.setDate(today.getDate() + i);
            const option = document.createElement('option');

            // Форматирование даты для значения option
            const formattedDate = date.toLocaleDateString('en-GB', {
                day: '2-digit',
                month: '2-digit',
                year: '2-digit'
            }).replace(/\//g, '-');

            option.value = formattedDate;

            // Форматирование даты для отображения
            option.textContent = date.toLocaleDateString('en-EN', {
                day: 'numeric',
                month: 'long'
            });

            dateSelect.appendChild(option);
        }
    }

    // Функция для создания списка времени с шагом в 2 часа
    function populateTimes() {
        const startHour = 10;
        const endHour = 20;
        const interval = 2;

        timeSelect.innerHTML = '<option value="" disabled selected>Choose time</option>'; // Сбросить текущие опции

        for (let hour = startHour; hour <= endHour; hour += interval) {
            const option = document.createElement('option');

            // Форматирование времени в формате HH:mm
            const timeString = `${hour.toString().padStart(2, '0')}:00`;
            option.value = timeString;
            option.textContent = timeString;
            timeSelect.appendChild(option);
        }
    }

    // Заполнить даты при загрузке страницы
    populateDates();

    // Событие на изменение даты для обновления времени
    dateSelect.addEventListener('change', function () {
        populateTimes(); // Обновить доступные временные слоты
    });

    // Проверка заполнения полей формы перед отправкой
    document.getElementById('appointmentForm').addEventListener('submit', function(event) {
        const phoneInput = document.getElementById('phone');
        if (!phoneInput.value || !dateSelect.value || !timeSelect.value) {
            alert('Please, complete all form!');
            event.preventDefault();
        }
    });
});
