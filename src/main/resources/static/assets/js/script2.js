//////////////////////////////////// 요소가 로드된 이후 보이기
const wrapper = document.querySelector('.wrapper');
wrapper.classList.remove('fouc');

//////////////////////////////////// 커스텀 드롭다운
// (이건 대충 챗GPT가 만들어준 코드라 다른 코드 쓰셔도 돼요!!)
const dropdowns = document.querySelectorAll('.dropdown');

// 각 드롭다운에 대한 이벤트 처리
dropdowns.forEach((dropdown) => {
    const selectedOption = dropdown.querySelector('.dropdown__selected');
    const optionList = dropdown.querySelector('.dropdown__list');
    const options = dropdown.querySelectorAll('.option');

    function toggleDropdown() {
        if (!dropdown.classList.contains('dropdown--disabled')) {
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
            if (!dropdown.classList.contains('dropdown--disabled')) {
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

//////////////////////////////////// 툴팁 표시하기
let tooltipElem;
document.onmouseover = function(event) {
    let target = event.target;
    // console.log(target);

    // data-tooltip 속성이 있는 요소
    let tooltipHtml = target.dataset.tooltip;
    if (!tooltipHtml) return;

    // 툴팁 요소를 만듭니다.

    tooltipElem = document.createElement('div');
    tooltipElem.className = 'tooltip';
    tooltipElem.innerHTML = tooltipHtml;
    document.body.append(tooltipElem)
    setTimeout(() => {
        tooltipElem.style.opacity = 1;
    }, 200)
    
    // 툴팁 요소를 data-tooltip 속성이 있는 요소 오른쪽, 가운데에 위치시킵니다.
    let coords = target.getBoundingClientRect();

    let left = coords.left + target.offsetWidth + 10; // 오른쪽에 위치하도록 설정
    if (left + tooltipElem.offsetWidth > window.innerWidth) {
    // 툴팁이 화면 오른쪽을 넘어가면 조절
    left = window.innerWidth - tooltipElem.offsetWidth;
    if (left < 0) left = 0;
    }

    let top = coords.top + (target.offsetHeight - tooltipElem.offsetHeight) / 2; // 세로 가운데에 위치하도록 설정
    if (top < 0) top = 0; // 툴팁이 화면 위를 넘지 않도록 조절

    tooltipElem.style.left = left + 'px';
    tooltipElem.style.top = top + 'px';
    };

document.onmouseout = function(e) {

    if (tooltipElem) {
    tooltipElem.remove();
    tooltipElem = null;
    }
};

//////////////////////////////////// 탭 내용 표시
// (이건 대충 챗GPT가 만들어준 코드라 다른 코드 써주세요!! 이 코드는 문제가 있어요!!)
// HTML 요소를 선택합니다.
const tabItems = document.querySelectorAll('.tab__item');
const tabContents = document.querySelectorAll('.tab__content');

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
            item.classList.remove('tab__item--active');
        });

        // 클릭한 탭 항목을 활성화 스타일로 변경합니다.
        tabItem.classList.add('tab__item--active');
    });
});