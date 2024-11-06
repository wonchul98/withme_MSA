import { useQuery } from "@tanstack/react-query";
import { API_URL } from "@/util/constants";
import axios from '@/util/axiosConfigClient';

interface Workspace {
    id: number;
    name: string;
    thumnail: string;
    repo_url: string;
    is_created: number; // 1 또는 0으로 생성 여부를 나타냄
    create_at: string; // ISO 날짜 형식
    readme_content: string;
  }
  
  interface Data {
    visible_workspaces: Workspace[];
    invisible_workspaces: Workspace[];
  }
  
  interface ApiResponse {
    status: number;
    message: string;
    isSuccess: boolean;
    data: Data;
    timestamp: string; // ISO 날짜 형식
  }
  

export function useUserSyncQuery() {
    return useQuery<ApiResponse, Error>({
      queryKey: [`userSync`],
      queryFn: async () => {
        const response = await axios.get<ApiResponse>(
          `${process.env.NEXT_PUBLIC_BACKEND_URL}${API_URL.SYNC}`,
        );
        console.log(response.data);
        return response.data; // 응답 데이터 반환
      },
      staleTime: 1000,
    });
  }
  