// import { create } from 'zustand';
// import axios from 'axios';

// const useServerStore = create((set) => ({
//   server: null,
//   categories: [],
//   channels: [],

//   // createserver?
//   updateServer: async (serverId, userId, serverName, serverDescription, img, openRoom) => {
//     try {
//       const formdata = new FormData();
//       formdata.append("requestDto", JSON.stringify({
//         serverId: serverId,
//         userId: userId,
//         name: serverName,
//         description: serverDescription,
//         profile: img,
//         open: openRoom,
//       }));

//       const res = await axios.patch(`${URL}/community/server/${serverId}/${userId}`, formdata, {
//         headers: {
//           'Content-Type': 'multipart/form-data',
//         },
//       });

//       if (res.data.success) {
//         set({ server: res.data });
//         alert("수정되었습니다");
//       } else {
//         alert("수정하는 중에 오류가 발생했습니다");
//         console.error(res);
//       }
//     } catch (error) {
//       alert("수정하는 중에 오류가 발생했습니다");
//       console.error('Error submitting update:', error);
//     }
//   },

//   deleteServer: async (serverId, userId, navigate) => {
//     const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
//     if (confirmDelete) {
//       try {
//         const res = await axios.delete(`${URL}/api/community/server`, {
//           data: {
//             serverId: serverId,
//             userId: userId,
//           },
//         });
//         if (res.data.success) {
//           alert("삭제되었습니다");
//           navigate('/main');
//         } else {
//           alert("삭제하는 중에 오류가 발생했습니다");
//           console.error(res);
//         }
//       } catch (error) {
//         alert("삭제하는 중에 오류가 발생했습니다");
//         console.error('Error deleting server:', error);
//       }
//     }
//   },
  
//   setServerInfo: (server) => set({ server }),
  
//   getServerInfo: async (serverId, userId) => {
//     try {
//       const res = await axios.get(`/community/server/${serverId}/${userId}`);
//       if (res.success) {
//         set({ 
//           server: res.data.server,
//           categories: res.data.categories,
//           channels: res.data.channel
//          });
//       } else {
//         console.log("Failed to fetch server data");
//       }
//     } catch (error) {
//       console.error(error);
//     }
//   },
  
//   setCategories: (categories) => set({ categories }),
//   setChannels: (channels) => set({ channels }),

//   addCategory: async ( datas ) => {
//     try {
//       const data = JSON.stringify(datas);
//       await axios.post('/community/category', data)
//       .then(res => {
//         if (res.success) {
//           set((state) => ({ categories: [...state.categories, res.data] }));
//         } else {
//           console.log("Failed to add category");
//         }
//       });
//     } catch (error) {
//       console.error(error);
//     }
//   },

//   addChannel: async ( datas ) => {
//     const data = JSON.stringify(datas);
//     try {
//       await axios.post('/community/channel', data)
//       .then(res => {  
//         if (res.success) {
//           set((state) => ({ channels: [...state.channels, res.data] }));
//         } else {
//           console.log("Failed to add channel");
//         }
//       })
//     } catch (error) {
//       console.error(error);
//     }
//   },

//   deleteCategory: async ( datas ) => {
//     const data = JSON.stringify(datas);
//     try {
//       await axios.delete('/community/category', data)
//       .then( res => {
//         if (res.success) {
//           set((state) => 
//             ({ categories: state.categories.filter(category => category.id !== res.categoryId) }));
//         } else {
//           console.log("Failed to delete category");
//         }
//       })
//     } catch (error) {
//       console.error(error);
//     }
//   },
  
//   deleteChannel: async ( datas ) => {
//     const data = JSON.stringify(datas);
//     try {
//       await axios.delete(`/community/channel`, data)
//       .then( res => {
//         if (res.success) {
//           set((state) => 
//             ({ channels: state.channels.filter(channel => channel.id !== datas.channelId) }));
//         } else {
//           console.log("Failed to delete channel");
//         }
//       })
//     } catch (error) {
//       console.error(error);
//     }
//   },

//   updateCategory: async ( datas ) => {
//     const data = JSON.stringify(datas);
//     try {
//       await axios.patch(`community/category`, data)
//       .then(res => {
//         if (res.success) {
//           set((state) => ({
//             categories: state.categories.map(category =>
//               category.id === datas.categoryId ? res.data : category
//             ),
//           }));
//         } else {
//           console.log("Failed to update category");
//         }
//       })
//     } catch (error) {
//       console.error(error);
//     }
//   },

//   updateChannel: async ( datas ) => {
//     const data = JSON.stringify(datas);
//     try {
//       await axios.put(`/community/channel/`, data)
//       .then( res => {
//         if (res.status === 200) {
//           set((state) => ({
//             channels: state.channels.map(channel =>
//               channel.id === datas.channelId ? res.data : channel
//             ),
//           }));
//         } else {
//           console.log("Failed to update channel");
//         }
//       })
//     } catch (error) {
//       console.error(error);
//     }
//   }
// }));

// export default useServerStore;
