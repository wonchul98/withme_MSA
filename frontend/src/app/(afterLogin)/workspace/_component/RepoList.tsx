// components/RepoList.js
import React, { useState } from 'react';
import RepoCheck from './RepoCheck';

const RepoList = () => {
  const repos = ['WithMe', 'Semento', 'AltTab', 'Smartview', 'Potless', 'Tripeer', 'Moass', 'Devway'];
  const [selectedRepo, setSelectedRepo] = useState<number | null>(null);

  const handleClick = (index: number) => {
    setSelectedRepo(index === selectedRepo ? null : index);
  };
  return (
    <ul className="repo-list scrollbar">
      {repos.map((repo, index) => (
        <li key={index} className="repo-item flex justify-start items-center" onClick={() => handleClick(index)}>
          <div className="repo_check">{selectedRepo === index && <RepoCheck />}</div>
          <div>{repo}</div>
        </li>
      ))}
      <style jsx>{`
        .repo_check {
          width: 20px;
          height: inherit;
        }
        .repo-list {
          list-style: none;
          padding: 0;
          margin: 10px 0;
          height: 200px;
        }
        .repo-item {
          padding: 8px;
          cursor: pointer;
          transition: background-color 0.2s;
          display: flex;
        }
        .repo-item:hover {
          background-color: #f0f0f0;
        }
      `}</style>
    </ul>
  );
};

export default RepoList;
