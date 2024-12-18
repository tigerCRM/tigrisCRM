package com.tiger.crm.service.board;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mapper.BoardOpenCompanyMapper;
import com.tiger.crm.repository.mapper.CompanyOptionMapper;
import com.tiger.crm.repository.mapper.SystemBoardMapper;
import com.tiger.crm.repository.mapper.TicketMapper;
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
public class SystemBoardServiceImpl implements SystemBoardService{
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private SystemBoardMapper systemBoardMapper;

    @Autowired
    private BoardOpenCompanyMapper boardOpenCompanyMapper;
    
    //시스템 보드 리스트 가져오기
    @Override
    public PagingResponse<Map<String, Object>> getSystemBoardList(PagingRequest pagingRequest) {

        List<Map<String, Object>> systemBoardList = systemBoardMapper.getSystemBoardList(pagingRequest);

        int totalRecords = getSystemBoardListCount(pagingRequest);

        return new PagingResponse<>(systemBoardList, totalRecords, pagingRequest);
    }

    //게시글 갯수 가져오기
    @Override
    public int getSystemBoardListCount(PagingRequest pagingRequest) {
        Integer count = systemBoardMapper.getSystemBoardListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }

    //시스템관리 글 저장
    @Override
    public int insertSystemBoard(SystemBoardDto systemBoardDto, BoardOpenCompanyDto boardOpenCompanyDto) {
        systemBoardMapper.insertSystemBoard(systemBoardDto);
        boardOpenCompanyDto.setBoardId(systemBoardDto.getBoardId());
        int resultCount = boardOpenCompanyMapper.insertBoardOpenCompany(boardOpenCompanyDto);

        if(resultCount != 1){
            LOGGER.info("insertSystemBoard ERROR occured!");
            return 0;
        }
        return systemBoardDto.getBoardId(); // 저장 실패시 0, 저장 성공시 boardId 리턴
    }
    
    //첨부파일 저장 후 boardTable 에 첨부파일 아이디 업데이트
    @Override
    public void setSystemBoardFileId(String fileId,int boardId) {
        systemBoardMapper.updateSystemBoardFileId(fileId,boardId);
    }
    
    //게시글번호로 게시글 상세 조회
    @Override
    public SystemBoardDto getSystemBoardByBoardId(int boardId) {
        return systemBoardMapper.getSystemBoardByBoardId(boardId);
    }
    
    //게시글삭제
    @Override
    public void deleteSystemBoardByBoardId(int boardId){
        //게시글 삭제
        systemBoardMapper.deleteSystemBoardByBoardId(boardId);
        //t_open_company 삭제
        boardOpenCompanyMapper.deleteBoardOpenCompany(boardId);
    }
    
    //게시글수정
    @Override
    public void updateSystemBoard(SystemBoardDto systemBoardDto){
        systemBoardMapper.updateSystemBoard(systemBoardDto);
    }
}
