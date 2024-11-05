// import { useQuery } from '@tanstack/react-query'; 
// import { API_URL } from '@/util/constants';
// import axios from 'axios';

// export function useGithubLoginQuery() {
//     return useQuery<void, Error>({
//       queryKey: [`getGithubLoginQuery`],
//       queryFn: async () => {
//         const response = await axios.get(`${process.env.VITE_SERVER_ENDPOINT}${API_URL.LOGIN}`);
//         if (response.status === 200) {
//           window.location.href = response.data.redirectUrl; 
//         }
//       },
//       retry: false, 
//     });
//   }