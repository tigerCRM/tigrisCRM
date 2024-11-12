// 각 드롭다운에 대한 이벤트 처리
dropdowns.forEach((dropdown) => {
    const selectedOption = dropdown.querySelector('.dropdown-selected');
    const optionList = dropdown.querySelector('.dropdown-list');
    const options = dropdown.querySelectorAll('.option');

    function toggleDropdown() {
        if (!dropdown.classList.contains('disabled')) {
            optionList.classList.toggle('active');
        }
    }

    function selectOption(option) {
        selectedOption.querySelector('p').textContent = option.textContent;
        toggleDropdown();
    }

    selectedOption.addEventListener('click', toggleDropdown);

    options.forEach((option) => {
        option.addEventListener('click', () => {
            if (!dropdown.classList.contains('disabled')) {
                selectOption(option);
            }
        });
    });

    document.addEventListener('click', (event) => {
        if (!dropdown.contains(event.target)) {
            optionList.classList.remove('active');
        }
    });
});


//////////////////////////////////// 탭 내용 표시
// (이건 대충 챗GPT가 만들어준 코드라 다른 코드 쓰셔도 돼요!!)
// HTML 요소를 선택합니다.
const tabItems = document.querySelectorAll('.tab-item');
const tabContents = document.querySelectorAll('.tab-content');

// 각 탭 항목에 클릭 이벤트를 추가합니다.
tabItems.forEach((tabItem, index) => { 
    tabItem.addEventListener('click', (event) => {
        event.preventDefault(); // 링크의 기본 동작 중지

        // 모든 탭 내용을 숨깁니다.
        tabContents.forEach(content => {
            content.style.display = 'none';
        });

        // 클릭한 탭 내용을 표시합니다.
        tabContents[index].style.display = 'block';

        // 모든 탭 항목의 활성화 스타일을 제거합니다.
        tabItems.forEach(item => {
            item.classList.remove('active');
        });

        // 클릭한 탭 항목을 활성화 스타일로 변경합니다.
        tabItem.classList.add('active');
    });
});

