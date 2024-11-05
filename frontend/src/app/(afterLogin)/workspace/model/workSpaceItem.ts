// 페이지 정보를 포함한 인터페이스
export interface Pageable {
    pageNumber: number;
    pageSize: number;
    sort: Sort[];
    offset: number;
    paged: boolean;
    unpaged: boolean;
  }
  
  // 정렬 정보를 포함한 인터페이스
  export interface Sort {
    direction: string;
    property: string;
    ignoreCase: boolean;
    nullHandling: string;
    ascending: boolean;
    descending: boolean;
  }
  
  // 작업 공간 내용
  export interface WorkspaceContent {
    id: number;
    name: string;
    thumbnail: string;
    repoUrl: string;
    isCreated: boolean;
    readmeContent: string;
    isPrivate: boolean;
  }
  
  // 최상위 응답 데이터 구조
  export interface WorkspaceResponse {
    status: number;
    message: string;
    code: null | string; // code는 null이거나 string일 수 있음
    isSuccess: boolean;
    data: {
      content: WorkspaceContent[];
      pageable: Pageable;
      size: number;
      number: number;
      sort: Sort[];
      numberOfElements: number;
      first: boolean;
      last: boolean;
      empty: boolean;
    };
    timestamp: string; // timestamp는 ISO 8601 형식의 문자열
  }
  
  // 최종적으로 API 응답의 타입
  export interface ApiResponse {
    data: WorkspaceResponse;
  }
  