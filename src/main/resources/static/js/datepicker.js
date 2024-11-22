document.addEventListener("DOMContentLoaded", function () {
    // 기본 Datepicker 설정 옵션
    const defaultOptions = {
        format: 'yyyy-mm-dd',   // 기본 날짜 형식 (변경 가능)
        autohide: true,
        language: 'ko',          // 한국어 설정
        onSelect: function () {
            // 날짜를 선택한 후, 달력을 강제로 숨깁니다.
            this.hide();
        }
    };
    const calendarButtons = document.querySelectorAll('.calendar-btn');
    calendarButtons.forEach(button => {
        button.addEventListener('click', function() {
            const input = this.previousElementSibling;
            if (input.datepickerInstance) {
                input.datepickerInstance.show();
            }
        });
    });
     // Datepicker 초기화 함수
    function initializeDatepicker(selector, options = {}) {
        const elements = document.querySelectorAll(selector);
        elements.forEach(element => {
            const datepickerInstance = new Datepicker(element, { ...defaultOptions, ...options });
            element.datepickerInstance = datepickerInstance;  // 인스턴스를 엘리먼트에 저장하여 재사용 가능
        });
    }

    // 시작 및 종료 날짜 선택기 초기화
    initializeDatepicker('.datepicker-input');
});