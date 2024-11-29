document.addEventListener("DOMContentLoaded", function () {
    // 모든 시작일과 종료일 필드 선택
    const startDateFields = document.querySelectorAll('.start-date');
    const endDateFields = document.querySelectorAll('.end-date');

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

    // 유효성 검사 추가: 시작일이 종료일 이후인지 확인
    startDateFields.forEach((startField, index) => {
        const endField = endDateFields[index]; // 시작일과 종료일의 쌍을 맞춤

        startField.addEventListener('change', () => {
            if (endField) {
                const startDate = new Date(startField.value);
                const endDate = new Date(endField.value);

                // 날짜가 유효한지 확인
                if (startDate == "Invalid Date" || endDate == "Invalid Date") return;

                // 시작일이 종료일보다 늦으면 종료일 초기화
                if (startDate > endDate) {
                    alert("종료일은 시작일 이후여야 합니다.");
                    endField.value = ''; // 종료일 초기화
                }

                // 종료일 최소값 설정
                if (startField.value) {
                    endField.min = startField.value;
                }
            }
        });

        if (endField) {
            endField.addEventListener('change', () => {
                const startDate = new Date(startField.value);
                const endDate = new Date(endField.value);

                // 날짜가 유효한지 확인
                if (startDate == "Invalid Date" || endDate == "Invalid Date") return;

                if (endDate < startDate) {
                    alert("종료일은 시작일 이후여야 합니다.");
                    endField.value = ''; // 종료일 초기화
                }
            });
        }
    });

    // 시작 및 종료 날짜 선택기 초기화
    initializeDatepicker('.datepicker-input');
});