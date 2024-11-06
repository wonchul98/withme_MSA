import { useQuery } from "@tanstack/react-query";
import { API_URL } from "@/util/constants";
import axios from '@/util/axiosConfigClient';

// WorkspaceItem 인터페이스
interface WorkspaceItem {
    id: number;
    name: string;
    thumbnail: string;
    repoUrl: string;
    isCreated: boolean;
    readmeContent: string;
    isPrivate: boolean;
  }
  
  // API 응답 인터페이스
  interface ApiResponse {
    status: number;
    message: string;
    code: string | null;
    isSuccess: boolean;
    data: WorkspaceItem[];
    timestamp: string;
  }
  

export function useUserRepoQuery() {
    return useQuery<ApiResponse, Error>({
      queryKey: [`userReopKey`],
      queryFn: async () => {
        const response = await axios.get<ApiResponse>(
          `${process.env.NEXT_PUBLIC_BACKEND_URL}${API_URL.WORKSPACE_X}`,
        );
        return response.data; // 응답 데이터 반환
      },
      staleTime: 100000,
    });
  }
  