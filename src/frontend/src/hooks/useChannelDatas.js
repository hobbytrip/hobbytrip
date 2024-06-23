import useServerStore from "../actions/useServerStore";

function useChannelDatas(channelId, channels) {
  const channel = channels.find(
    (channel) => String(channel.channelId) === String(channelId)
  );

  const getChannelName = () => {
    return channel ? channel.name : "Unknown Channel";
  };

  const getChannelTypeIcon = () => {
    if (!channel) return "🚀";

    switch (channel.channelType) {
      case "CHAT":
        return "💬";
      case "VOICE":
        return "🎤";
      case "FORUM":
        return "📝";
      default:
        return "🚀";
    }
  };

  return { getChannelName, getChannelTypeIcon };
}

export default useChannelDatas;
