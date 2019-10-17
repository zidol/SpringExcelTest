package com.test.excel.vo;

import java.io.Serializable;

public class FileVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileVO() {
	}

	public FileVO(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	/**
	 * 첨부파일 아이디
	 */
	public String atchFileId = "";
	/**
	 * 생성일자
	 */
	public String creatDt = "";
	/**
	 * 파일확장자
	 */
	public String fileExtsn = "";
	/**
	 * 파일크기
	 */
	public String fileMg = "";
	/**
	 * 파일연번
	 */
	public String fileSn = "";
	/**
	 * 파일저장경로
	 */
//	public String fileStreCours = "";
	/**
	 * 원파일명
	 */
	public String orignlFileNm = "";
	/**
	 * 저장파일명
	 */
	public String streFileNm = "";

	/**
	 * atchFileId attribute를 리턴한다.
	 * 
	 * @return the atchFileId
	 */
	public int fileWidth = 0;

	/*
	 * 미리보기 가능한지에 대한 유무 데이터셋 화면단에서 쓰임 DB데이터와 관련없음
	 */
	private boolean previewPosible = false;

	// 확장자 제거한 파일명
	private String noExtsnFileName = "";

	public int getFileWidth() {
		return fileWidth;
	}

	public void setFileWidth(int fileWidth) {
		this.fileWidth = fileWidth;
	}

	public String getAtchFileId() {
		return atchFileId;
	}

	/**
	 * atchFileId attribute 값을 설정한다.
	 * 
	 * @param atchFileId the atchFileId to set
	 */
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	/**
	 * creatDt attribute를 리턴한다.
	 * 
	 * @return the creatDt
	 */
	public String getCreatDt() {
		return creatDt;
	}

	/**
	 * creatDt attribute 값을 설정한다.
	 * 
	 * @param creatDt the creatDt to set
	 */
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}

	/**
	 * fileExtsn attribute를 리턴한다.
	 * 
	 * @return the fileExtsn
	 */
	public String getFileExtsn() {
		return fileExtsn;
	}

	/**
	 * fileExtsn attribute 값을 설정한다.
	 * 
	 * @param fileExtsn the fileExtsn to set
	 */
	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}

	/**
	 * fileMg attribute를 리턴한다.
	 * 
	 * @return the fileMg
	 */
	public String getFileMg() {
		return fileMg;
	}

	/**
	 * fileMg attribute 값을 설정한다.
	 * 
	 * @param fileMg the fileMg to set
	 */
	public void setFileMg(String fileMg) {
		this.fileMg = fileMg;
	}

	/**
	 * fileSn attribute를 리턴한다.
	 * 
	 * @return the fileSn
	 */
	public String getFileSn() {
		return fileSn;
	}

	/**
	 * fileSn attribute 값을 설정한다.
	 * 
	 * @param fileSn the fileSn to set
	 */
	public void setFileSn(String fileSn) {
		this.fileSn = fileSn;
	}

	/**
	 * fileStreCours attribute를 리턴한다.
	 * 
	 * @return the fileStreCours
	 */
//	public String getFileStreCours() {
//		return fileStreCours;
//	}

	/**
	 * fileStreCours attribute 값을 설정한다.
	 * 
	 * @param fileStreCours the fileStreCours to set
	 */
//	public void setFileStreCours(String fileStreCours) {
//		this.fileStreCours = fileStreCours;
//	}

	/**
	 * orignlFileNm attribute를 리턴한다.
	 * 
	 * @return the orignlFileNm
	 */
	public String getOrignlFileNm() {
		return orignlFileNm;
	}

	/**
	 * orignlFileNm attribute 값을 설정한다.
	 * 
	 * @param orignlFileNm the orignlFileNm to set
	 */
	public void setOrignlFileNm(String orignlFileNm) {
		this.orignlFileNm = orignlFileNm;
	}

	/**
	 * streFileNm attribute를 리턴한다.
	 * 
	 * @return the streFileNm
	 */
	public String getStreFileNm() {
		return streFileNm;
	}

	/**
	 * streFileNm attribute 값을 설정한다.
	 * 
	 * @param streFileNm the streFileNm to set
	 */
	public void setStreFileNm(String streFileNm) {
		this.streFileNm = streFileNm;
	}

	@Override
	public String toString() {
		return "FileVO [atchFileId=" + atchFileId + ", creatDt=" + creatDt + ", fileExtsn=" + fileExtsn + ", fileMg="
				+ fileMg + ", fileSn=" + fileSn + ", orignlFileNm=" + orignlFileNm + ", streFileNm=" + streFileNm
				+ ", fileWidth=" + fileWidth + ", previewPosible=" + previewPosible + ", noExtsnFileName="
				+ noExtsnFileName + "]";
	}

}