//////////////////////////////////// 요소가 로드된 이후 보이기
const wrapper = document.querySelector('.wrapper');
wrapper.classList.remove('fouc');

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

// 서브 LNB 트리 열고닫기
let treeLists = document.getElementsByClassName('tree-list');
let toggleNode = document.getElementsByClassName('tree-arrow-btn');
for (let i = 0; i < toggleNode.length; i++) {
    toggleNode[i].addEventListener('click', (e)=> {
        let arrowbtn = e.target;
        let treeFolder = arrowbtn.closest('.tree-folder');
        if (treeFolder) {
            treeFolder.classList.toggle('active');
        }
        arrowbtn.classList.toggle('tree--open');
    })
}

// sub lnb 트리 구조 좌측 패딩
document.addEventListener('DOMContentLoaded', function() {
    let treeListSubs = document.querySelectorAll('[class^="tree-list--sub"]');
    treeListSubs.forEach(treeListSub => {
        let num = parseInt(treeListSub.classList[0].split('-')[4]); // 클래스명에서 [num] 추출
        let paddingValue = (num * 12 + 20) + 'px'; // 기본값 16을 더하여 최종 padding 값 계산
        let treeItems = treeListSub.querySelectorAll('.tree-item-title');
        treeItems.forEach(item => {
            item.style.paddingLeft = paddingValue;
        });
        // console.log(num, paddingValue)
    });
});