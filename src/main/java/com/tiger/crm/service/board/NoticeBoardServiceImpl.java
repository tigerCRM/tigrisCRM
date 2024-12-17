package com.tiger.crm.service.board;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.NoticeBoardDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mapper.BoardOpenCompanyMapper;
import com.tiger.crm.repository.mapper.NoticeBoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
* 시스템 관리 게시판 service
* 작성자 : 제예솔
* */
@Service
public class NoticeBoardServiceImpl implements NoticeBoardService{
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private NoticeBoardMapper noticeBoardMapper;

    @Autowired
    private BoardOpenCompanyMapper boardOpenCompanyMapper;
    
    //시스템 보드 리스트 가져오기
    @Override
    public PagingResponse<Map<String, Object>> getNoticeBoardList(PagingRequest pagingRequest) {

        List<Map<String, Object>> noticeBoardList = noticeBoardMapper.getNoticeBoardList(pagingRequest);

        int totalRecords = getNoticeBoardListCount(pagingRequest);

        return new PagingResponse<>(noticeBoardList, totalRecords, pagingRequest);
    }

    //게시글 갯수 가져오기
    @Override
    public int getNoticeBoardListCount(PagingRequest pagingRequest) {
        Integer count = noticeBoardMapper.getNoticeBoardListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }

    //시스템관리 글 저장
    @Override
    public int insertNoticeBoard(NoticeBoardDto noticeBoardDto, List<BoardOpenCompanyDto> boardOpenCompanyList) {

        if("N".equals(noticeBoardDto.getPopupYn()) ){
            noticeBoardDto.setPopupStartDt(null);
            noticeBoardDto.setPopupEndDt(null);
        }

        noticeBoardMapper.insertNoticeBoard(noticeBoardDto);
        int boardId = noticeBoardDto.getBoardId();
        int resultCount = 0;
        if("N".equals(noticeBoardDto.getOpenYn()) && boardOpenCompanyList != null){ //공개여부 N 이고, 공개회사 리스트를 받았을 경우
            for(BoardOpenCompanyDto boardOpenCompany : boardOpenCompanyList){
                boardOpenCompany.setBoardId(boardId);
                resultCount += boardOpenCompanyMapper.insertBoardOpenCompany(boardOpenCompany);
            }

            if(resultCount == 0){
                LOGGER.info("insertNoticeBoard ERROR occured!");
                return 0;
            }
        }
        return boardId; // 저장 실패시 0, 저장 성공시 boardId 리턴
    }
    
    //첨부파일 저장 후 boardTable 에 첨부파일 아이디 업데이트
    @Override
    public void setNoticeBoardFileId(String fileId,int boardId) {
        noticeBoardMapper.updateNoticeBoardFileId(fileId,boardId);
    }
    
    //게시글번호로 게시글 상세 조회
    @Override
    public NoticeBoardDto getNoticeBoardByBoardId(int boardId) {
        return noticeBoardMapper.getNoticeBoardByBoardId(boardId);
    }
    
    //게시글삭제
    @Override
    public void deleteNoticeBoardByBoardId(int boardId){
        //게시글 삭제
        noticeBoardMapper.deleteNoticeBoardByBoardId(boardId);
        //t_open_company 삭제
        boardOpenCompanyMapper.deleteBoardOpenCompany(boardId);
    }
    
    //게시글수정
    @Override
    public void updateNoticeBoard(NoticeBoardDto noticeBoardDto, List<BoardOpenCompanyDto> boardOpenCompanyList){
        int resultCount = 0;
        if("N".equals(noticeBoardDto.getPopupYn()) ){
            noticeBoardDto.setPopupStartDt(null);
            noticeBoardDto.setPopupEndDt(null);
        }
        if("Y".equals(noticeBoardDto.getOpenYn())){ //공개여부 Y 일 경우, 공개 회사 리스트를 지운다
            boardOpenCompanyMapper.deleteBoardOpenCompany(noticeBoardDto.getBoardId());
        }else if("N".equals(noticeBoardDto.getOpenYn()) && boardOpenCompanyList != null){//공개여부 N 이고, 공개회사 리스트를 받았을 경우
            boardOpenCompanyMapper.deleteBoardOpenCompany(noticeBoardDto.getBoardId());//초기화
            for(BoardOpenCompanyDto boardOpenCompany : boardOpenCompanyList){
                boardOpenCompany.setBoardId(noticeBoardDto.getBoardId());
                resultCount += boardOpenCompanyMapper.insertBoardOpenCompany(boardOpenCompany);
            }
        }
        noticeBoardMapper.updateNoticeBoard(noticeBoardDto);
    }

    //게시판 번호로 boardOpenCompany 찾아오기
    @Override
    public List<BoardOpenCompanyDto> getBoardOpenCompanyByBoardId(int boardId){
        return boardOpenCompanyMapper.selectBoardOpenCompanyByBoardId(boardId);
    }
}
